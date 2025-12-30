package com.github.bratek20.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create

enum class B20PublishComponent(val componentName: String) {
    JAVA("java"),
    JAVA_PLATFORM("javaPlatform"),
    VERSION_CATALOG("versionCatalog"),
    BOOT_JAR("bootJar"),
}

open class B20PublishExtension {
    var component: B20PublishComponent = B20PublishComponent.JAVA
    var publicationName: String = "maven"

    var groupId: String? = null
    var artifactId: String? = null
    var version: String? = null
}

class B20Publish : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.apply("maven-publish")

        val ext = project.extensions.create<B20PublishExtension>("b20Publish")

        project.afterEvaluate {
            val componentName = ext.component.componentName

            val groupId = ext.groupId
                ?: project.group.toString().takeIf { it.isNotBlank() }
                ?: error("b20Publish.groupId must be set")

            val artifactId = ext.artifactId
                ?: project.name.takeIf { it.isNotBlank() }
                ?: error("b20Publish.artifactId must be set")

            val version = calculateVersion(project, ext)

            project.logger.lifecycle(
                """
                [B20Publish] Final publish configuration for ${project.name}:
                  • Component:    $componentName
                  • Group:        $groupId
                  • Artifact:     $artifactId
                  • Version:      $version
                """.trimIndent()
            )

            project.extensions.configure<PublishingExtension> {
                publications {
                    create<MavenPublication>(ext.publicationName) {
                        when (ext.component) {
                            B20PublishComponent.BOOT_JAR -> {
                                artifact(project.tasks.getByName("bootJar"))
                            }
                            else -> {
                                val component = project.components.findByName(componentName)
                                    ?: error(
                                        "Component '$componentName' not found. " +
                                                "Available components: ${project.components.joinToString { it.name }}"
                                    )
                                from(component)
                            }
                        }

                        this.groupId = groupId
                        this.artifactId = artifactId
                        this.version = version
                    }
                }

                repositories {
                    maven {
                        name = "tsg"
                        url = TSG_ARTIFACTORY_LIBS_RELEASE_LOCAL_URI
                        credentials {
                            username = project.resolveProperty("artifactoryUsername")
                            password = project.resolveProperty("artifactoryPassword")
                        }
                    }
                }
            }
        }
    }

    private fun calculateVersion(project: Project, ext: B20PublishExtension): String {
        return ext.version
            ?: project.version.toString().takeIf { it.isNotBlank() && it != "unspecified" }
            ?: error("b20Publish.version must be set")
    }
}

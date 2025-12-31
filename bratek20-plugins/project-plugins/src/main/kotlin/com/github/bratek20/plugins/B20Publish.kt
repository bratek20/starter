package com.github.bratek20.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import java.net.URI

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
                [B20Publish] Publish configuration for ${project.name}:
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
                    project.tryResolveProperty(GITHUB_ACTOR)?.let { resolvedUsername ->
                        maven {
                            name = "b20-starter"
                            url = B20_STARTER_URI
                            credentials {
                                username = resolvedUsername
                                password = project.resolveProperty(GITHUB_TOKEN)
                            }
                        }
                        project.logger.lifecycle("[B20Publish] B20 starter repository configured for publishing")
                    }

                    project.tryResolveProperty("artifactoryUsername")?.let { resolvedUsername ->
                        maven {
                            name = "tsg"
                            url = URI.create("https://artifactory.devs.tensquaregames.com/artifactory/libs-release-local")
                            credentials {
                                username = resolvedUsername
                                password = project.resolveProperty("artifactoryPassword")
                            }
                        }
                        project.logger.lifecycle("[B20Publish] TSG Artifactory repository configured for publishing")
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

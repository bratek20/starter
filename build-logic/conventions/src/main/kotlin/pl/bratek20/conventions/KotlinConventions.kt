package pl.bratek20.conventions

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import pl.bratek20.extensions.versionCatalog

class KotlinConventions: Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(plugins) {
                apply("org.jetbrains.kotlin.jvm")
            }

            val javaVersion = JavaVersion.toVersion(versionCatalog().findVersion("java").get())

            project.tasks.withType(KotlinCompile::class.java) {
                kotlinOptions {
                    jvmTarget = javaVersion.majorVersion
                }
            }

            with(dependencies) {
                add("testImplementation", versionCatalog().findLibrary("junit-jupiter-api").get())
                add("testImplementation", versionCatalog().findLibrary("junit-jupiter-params").get())
                add("testRuntimeOnly", versionCatalog().findLibrary("junit-jupiter-engine").get())
            }

            tasks.withType(Test::class.java).configureEach {
                useJUnitPlatform()
            }
        }
    }
}
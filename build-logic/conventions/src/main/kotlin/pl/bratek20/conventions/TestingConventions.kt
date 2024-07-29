package pl.bratek20.conventions

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import pl.bratek20.extensions.versionCatalog

class TestingConventions: Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(dependencies) {
                add("testImplementation", versionCatalog().findLibrary("junit-jupiter-api").get())
                add("testImplementation", versionCatalog().findLibrary("junit-jupiter-params").get())
                add("testRuntimeOnly", versionCatalog().findLibrary("junit-jupiter-engine").get())

                add("testImplementation", versionCatalog().findLibrary("assertj-core").get())
            }

            tasks.withType(Test::class.java).configureEach {
                useJUnitPlatform()
            }
        }
    }
}
package pl.bratek20.plugins.conventions

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test

class BaseConventions : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            plugins.apply {
                apply("pl.bratek20.internal.repositories-conventions")

                apply("java")

                apply("io.freefair.lombok")

            }

            val javaPluginExtension = extensions.findByType(JavaPluginExtension::class.java)
            javaPluginExtension?.sourceCompatibility = JavaVersion.VERSION_17

            // Configure dependencies
            dependencies.apply {
                // Testing
                add("testImplementation", platform("org.junit:junit-bom:5.9.1"))
                add("testImplementation", "org.junit.jupiter:junit-jupiter")

                // Assertions
                add("testImplementation", "org.assertj:assertj-core:3.24.2")
            }

            tasks.withType(Test::class.java).configureEach {
                useJUnitPlatform()
            }
        }
    }
}
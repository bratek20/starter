package pl.bratek20.plugins.conventions

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import pl.bratek20.plugins.conventions.internal.RepositoriesConventions

class BaseConventions : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(plugins) {
                apply(RepositoriesConventions::class.java)

                apply(JavaPlugin::class.java)

                apply("io.freefair.lombok")

                apply(SpringLibraryConventions::class.java)

            }

            val javaPluginExtension = extensions.findByType(JavaPluginExtension::class.java)
            javaPluginExtension?.sourceCompatibility = JavaVersion.VERSION_17

            // Configure dependencies
            with(dependencies) {
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
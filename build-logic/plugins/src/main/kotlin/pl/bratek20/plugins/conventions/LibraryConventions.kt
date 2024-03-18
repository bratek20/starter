package pl.bratek20.plugins.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaTestFixturesPlugin

class LibraryConventions : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(plugins) {
                apply(BaseConventions::class.java)

                apply(PublishConventions::class.java)

                apply(JavaLibraryPlugin::class.java)

                apply(JavaTestFixturesPlugin::class.java)
            }

            with(dependencies) {
                // dependency injection
                add("testFixturesImplementation", "org.springframework:spring-context:6.1.2")

                // assertions
                add("testFixturesImplementation", "org.assertj:assertj-core:3.24.2")

                // testing
                add("testFixturesImplementation", "org.junit.jupiter:junit-jupiter-api:5.9.1")
            }
        }
    }
}
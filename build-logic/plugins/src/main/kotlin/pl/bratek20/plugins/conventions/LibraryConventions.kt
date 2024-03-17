package pl.bratek20.plugins.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project

class LibraryConventions : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(plugins) {
                apply("pl.bratek20.base-conventions")

                apply("pl.bratek20.publish-conventions")

                apply("java-library")

                apply("java-test-fixtures")
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
package pl.bratek20.plugins.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra

class SpringLibraryConventions : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(plugins) {
                apply("org.springframework.boot")
                apply("io.spring.dependency-management")
            }

            extra["testcontainers.version"] = "1.16.2"

            tasks.named("bootJar").configure {
                enabled = false
            }
        }
    }
}
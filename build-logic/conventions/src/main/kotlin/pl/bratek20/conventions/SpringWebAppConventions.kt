package pl.bratek20.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import org.springframework.boot.gradle.plugin.SpringBootPlugin
import pl.bratek20.extensions.applyJavaPlugin
import pl.bratek20.extensions.versionCatalog

class SpringWebAppConventions : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(plugins) {
                apply(SpringAppConventions::class.java)
            }

            with(dependencies) {
                add("implementation", versionCatalog().findLibrary("spring-boot-swagger").get())
                // needed for swagger to not complain about missing loggers
                add("implementation", versionCatalog().findLibrary("bratek20-logs").get())
            }
        }
    }
}
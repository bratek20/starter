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
                apply(KotlinConventions::class.java)
                apply(SpringBootPlugin::class.java)
            }

            with(dependencies) {
                add("implementation", platform(versionCatalog().findLibrary("spring-boot-dependencies").get()))
                add("implementation", "org.springframework.boot:spring-boot-starter-web")
                add("implementation", versionCatalog().findLibrary("spring-boot-swagger").get())

                add("implementation", versionCatalog().findLibrary("bratek20-spring").get())
                add("implementation", versionCatalog().findLibrary("bratek20-logs").get())
            }
        }
    }
}
package pl.bratek20.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra
import org.springframework.boot.gradle.plugin.SpringBootPlugin

class SpringAppConventions : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(plugins) {
                apply(SpringBootPlugin::class.java)
            }

            //extra["testcontainers.version"] = "1.16.2"

//            tasks.named("bootJar").configure {
//                enabled = false
//            }
        }
    }
}
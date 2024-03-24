package pl.bratek20.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project

class SpringAppConventions : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(plugins) {
                apply(SpringLibraryConventions::class.java)
            }
            tasks.named("bootJar").configure {
                enabled = true
            }
        }
    }
}
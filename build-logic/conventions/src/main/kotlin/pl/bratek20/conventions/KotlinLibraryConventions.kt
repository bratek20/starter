package pl.bratek20.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinLibraryConventions: Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(plugins) {
                apply(InternalKotlinLibraryConventions::class.java)

                apply(PublishConventions::class.java)
            }
        }
    }
}
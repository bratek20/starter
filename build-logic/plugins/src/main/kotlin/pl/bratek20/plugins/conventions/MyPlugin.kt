package pl.bratek20.plugins.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(dependencies) {
                add("testImplementation", "org.assertj:assertj-core:3.24.2")
            }
            with(repositories) {
                mavenLocal()
            }
        }
    }
}
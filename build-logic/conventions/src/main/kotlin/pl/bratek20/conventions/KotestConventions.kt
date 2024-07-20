package pl.bratek20.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import pl.bratek20.extensions.versionCatalog

class KotestConventions: Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(dependencies) {
                add("testImplementation", versionCatalog().findLibrary("kotest-runner-junit5").get())
                add("testImplementation", versionCatalog().findLibrary("kotest-assertions-core").get())
            }
        }
    }
}
package pl.bratek20.plugins.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaTestFixturesPlugin
import pl.bratek20.plugins.extensions.versionCatalog

class LibraryConventions : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(plugins) {
                apply(SimpleLibraryConventions::class.java)

                apply(JavaTestFixturesPlugin::class.java)
            }

            with(dependencies) {
                // assertions
                add("testFixturesImplementation", versionCatalog().findLibrary("assertj-core").get())

                // testing
                add("testFixturesImplementation", versionCatalog().findLibrary("junit-jupiter-api").get())
            }
        }
    }
}
package pl.bratek20.plugins.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaTestFixturesPlugin
import pl.bratek20.plugins.extensions.versionCatalog

class SimpleLibraryConventions : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(plugins) {
                apply(BaseConventions::class.java)

                apply(JavaLibraryPlugin::class.java)

                apply(PublishConventions::class.java)
            }
        }
    }
}
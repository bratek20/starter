package pl.bratek20.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaTestFixturesPlugin
import pl.bratek20.extensions.applyJavaLibraryPlugin
import pl.bratek20.extensions.versionCatalog

class InternalKotlinLibraryConventions: Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(plugins) {
                apply(KotlinConventions::class.java)

                //TODO is java library needed? it forces to set java version
                applyJavaLibraryPlugin(project, this)

                apply(JavaTestFixturesPlugin::class.java)
            }

            with(dependencies) {
                add("testFixturesImplementation", versionCatalog().findLibrary("assertj-core").get())
            }
        }
    }
}
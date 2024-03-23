package pl.bratek20.plugins.conventions

import io.freefair.gradle.plugins.lombok.LombokPlugin
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import pl.bratek20.plugins.conventions.internal.RepositoriesConventions
import pl.bratek20.extensions.versionCatalog

class BaseConventions : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(plugins) {
                apply(RepositoriesConventions::class.java)

                apply(JavaPlugin::class.java)

                apply(LombokPlugin::class.java)

                apply(SpringLibraryConventions::class.java)
            }

            val javaPluginExtension = extensions.findByType(JavaPluginExtension::class.java)
            val javaVersion = JavaVersion.toVersion(versionCatalog().findVersion("java").get())
            javaPluginExtension?.sourceCompatibility = javaVersion
            javaPluginExtension?.targetCompatibility = javaVersion

            with(dependencies) {
                add("testImplementation", versionCatalog().findLibrary("junit-jupiter-api").get())
                add("testRuntimeOnly", versionCatalog().findLibrary("junit-jupiter-engine").get())

                add("testImplementation", versionCatalog().findLibrary("assertj-core").get())
            }

            tasks.withType(Test::class.java).configureEach {
                useJUnitPlatform()
            }
        }
    }
}
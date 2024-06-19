package pl.bratek20.conventions

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.toolchain.JavaLanguageVersion
import pl.bratek20.conventions.internal.RepositoriesConventions
import pl.bratek20.extensions.versionCatalog

class JavaConventions : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(plugins) {
                apply(RepositoriesConventions::class.java)

                apply(JavaPlugin::class.java)
            }

            val javaVersion = JavaVersion.toVersion(versionCatalog().findVersion("java").get())

            project.extensions.getByType(JavaPluginExtension::class.java)
                .toolchain.languageVersion
                    .set(JavaLanguageVersion.of(javaVersion.majorVersion))

            with(dependencies) {
                add("testImplementation", versionCatalog().findLibrary("junit-jupiter-api").get())
                add("testImplementation", versionCatalog().findLibrary("junit-jupiter-params").get())
                add("testRuntimeOnly", versionCatalog().findLibrary("junit-jupiter-engine").get())

                add("testImplementation", versionCatalog().findLibrary("assertj-core").get())
            }

            tasks.withType(Test::class.java).configureEach {
                useJUnitPlatform()
            }
        }
    }
}
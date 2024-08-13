package pl.bratek20.conventions

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.toolchain.JavaLanguageVersion
import pl.bratek20.extensions.versionCatalog

class JavaConventions : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(plugins) {
                apply(JavaPlugin::class.java)

                apply(TestingConventions::class.java)
            }

            val javaVersion = JavaVersion.toVersion(versionCatalog().findVersion("java").get())

            project.extensions.getByType(JavaPluginExtension::class.java)
                .toolchain.languageVersion
                    .set(JavaLanguageVersion.of(javaVersion.majorVersion))
        }
    }
}
package pl.bratek20.conventions

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.plugins.JavaTestFixturesPlugin
import org.gradle.jvm.toolchain.JavaLanguageVersion
import pl.bratek20.extensions.versionCatalog

class InternalKotlinLibraryConventions: Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(plugins) {
                apply(KotlinConventions::class.java)

                //TODO is java library needed? it forces to set java version
                apply(JavaLibraryPlugin::class.java)

                val javaVersion = JavaVersion.toVersion(versionCatalog().findVersion("java").get())

                project.extensions.getByType(JavaPluginExtension::class.java)
                    .toolchain.languageVersion
                    .set(JavaLanguageVersion.of(javaVersion.majorVersion))

                apply(JavaTestFixturesPlugin::class.java)
            }

            with(dependencies) {
                // AssertJ assertions
                add("testFixturesImplementation", versionCatalog().findLibrary("assertj-core").get())

                // Kotest assertions
                add("testFixturesImplementation", versionCatalog().findLibrary("kotest-assertions-core").get())
            }
        }
    }
}
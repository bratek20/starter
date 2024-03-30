package pl.bratek20.conventions

import io.freefair.gradle.plugins.lombok.LombokPlugin
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.platform.base.ToolChain
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import pl.bratek20.conventions.internal.RepositoriesConventions
import pl.bratek20.extensions.versionCatalog

class BaseConventions : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(plugins) {
                apply(RepositoriesConventions::class.java)

                apply(JavaPlugin::class.java)
                apply("org.jetbrains.kotlin.jvm")

                apply(LombokPlugin::class.java)
            }

            val javaVersion = JavaVersion.toVersion(versionCatalog().findVersion("java").get())

            project.extensions.getByType(JavaPluginExtension::class.java)
                .toolchain.languageVersion
                    .set(JavaLanguageVersion.of(javaVersion.majorVersion))

            project.tasks.withType(KotlinCompile::class.java) {
                kotlinOptions {
                    jvmTarget = javaVersion.majorVersion
                }
            }

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
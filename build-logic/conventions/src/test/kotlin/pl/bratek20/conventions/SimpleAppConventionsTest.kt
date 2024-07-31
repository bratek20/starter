package pl.bratek20.conventions

import org.assertj.core.api.Assertions.assertThat
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.util.jar.JarFile

class SimpleAppConventionsTest {

    @Test
    fun `plugin applies and finds main class`(@TempDir projectDir: File) {
        val buildFile = File(projectDir, "build.gradle.kts")
        val settingsFile = File(projectDir, "settings.gradle.kts")

        File(projectDir, "src/main/kotlin/myapp/Main.kt").apply {
            parentFile.mkdirs()
            writeText("""
                package myapp
                
                fun main(args: Array<String>) {
                    println("Hello, World!")
                }
            """.trimIndent())
        }

        // Write build script
        buildFile.writeText("""
            plugins {
                id("com.github.bratek20.simple-app-conventions")
            }
        """.trimIndent())

        // Write settings script
        settingsFile.writeText("""
            rootProject.name = "testProject"

            dependencyResolutionManagement {
                versionCatalogs {
                    create("libs") {
                        from("com.github.bratek20:version-catalog:1.0.58") // Specify your catalog version
                    }
                }

                repositories {
                    mavenLocal()
                    mavenCentral()

                    val githubActor: String? = if (extra.has("githubActor")) extra["githubActor"] as String else System.getenv("GITHUB_ACTOR")
                    val githubToken: String? = if (extra.has("githubToken")) extra["githubToken"] as String else System.getenv("GITHUB_TOKEN")

                    if (githubActor != null && githubToken != null) {
                        maven {
                            name = "GitHubPackages"
                            url = uri("https://maven.pkg.github.com/bratek20/starter")
                            credentials {
                                username = githubActor
                                password = githubToken
                            }
                        }
                    }
                }
            }
        """.trimIndent())

        // Run the build
        val result = GradleRunner.create()
            .withProjectDir(projectDir)
            .withPluginClasspath()
            .withArguments("build")
            .withDebug(true)
            .build()

        // Verify the result
        val mainClassFoundLine = result.output.lines().find { it.contains("Main class found:") }
        assertThat(mainClassFoundLine).isNotNull()

        assertThat(mainClassFoundLine).contains("Main class found: myapp.MainKt")

        // Verify the built jar contains the correct main class in the manifest
        val jarFile = File(projectDir, "build/libs/testProject.jar")
        assertThat(jarFile.exists()).isTrue()

        JarFile(jarFile).use { jar ->
            val manifest = jar.manifest
            val mainClass = manifest.mainAttributes.getValue("Main-Class")
            assertThat(mainClass).isEqualTo("myapp.MainKt")
        }
    }
}

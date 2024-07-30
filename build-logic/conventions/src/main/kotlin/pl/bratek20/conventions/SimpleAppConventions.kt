package pl.bratek20.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import pl.bratek20.extensions.applyJavaPlugin
import pl.bratek20.extensions.versionCatalog

class SimpleAppConventions : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(plugins) {
                apply(KotlinConventions::class.java)
                apply("com.github.johnrengelman.shadow")
            }

            tasks.named("shadowJar") {
//                archiveClassifier.set("") // optional, removes the "-all" suffix from the JAR name
//                manifest {
//                    attributes["Main-Class"] = "OstiumWebAppTesterKt" // Replace with your main class
//                }
            }

            tasks.named("build") {
                dependsOn(tasks.named("shadowJar"))
            }
        }
    }
}

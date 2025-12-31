package com.github.bratek20.plugins

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.named
import java.io.File

class B20SimpleApp : B20App() {
    override fun apply(project: Project) {
        super.apply(project)

        with(project) {
            plugins.apply("com.github.johnrengelman.shadow")

            val mainClassName = findMainClassName(project)
            if (mainClassName != null) {
                logger.lifecycle("Main class found: $mainClassName")
                tasks.named<ShadowJar>("shadowJar") {
                    archiveClassifier.set("") // removes the "-all" suffix from the JAR name
                    manifest {
                        attributes["Main-Class"] = mainClassName
                    }
                }

                tasks.named("build") {
                    dependsOn(tasks.named("shadowJar"))
                }
            } else {
                logger.error("Main class not found. Please create a kotlin file with main!")
            }
        }
    }

    private fun findMainClassName(project: Project): String? {
        val mainSrcDirs = project.sourceSets()["main"].allSource.srcDirs
        mainSrcDirs.forEach { dir ->
            val mainClassFile = findMainClassFile(dir)
            if (mainClassFile != null) {
                return toClassName(mainClassFile, project)
            }
        }
        return null
    }

    private fun findMainClassFile(dir: File): File? {
        return dir.walkTopDown().find { file ->
            file.extension == "kt" && file.readText().contains("fun main(")
        }
    }

    private fun toClassName(file: File, project: Project): String {
        val relativePath = file.toRelativeString(project.projectDir).removeSuffix(".kt")
        return relativePath
            .replace(File.separator, ".")
            .removePrefix("src.main.kotlin.")
            .plus("Kt")
    }
}


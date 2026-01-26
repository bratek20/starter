package com.github.bratek20.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.named

fun Project.sourceSets(): SourceSetContainer = extensions.getByName("sourceSets") as SourceSetContainer

class B20TestsInTestFixtures : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            dependencies {
                add("testFixturesImplementation", "org.junit.jupiter:junit-jupiter-api")
                add("testFixturesImplementation", "org.junit.jupiter:junit-jupiter-params")
            }

            tasks.named("check").configure {
                dependsOn("testFixturesClasses")
            }

            val sourceSets = project.sourceSets()

            tasks.named<Test>("test").configure {
                dependsOn("testFixturesClasses")
                testClassesDirs += sourceSets["testFixtures"].output.classesDirs
                classpath += sourceSets["testFixtures"].runtimeClasspath
            }
        }
    }
}
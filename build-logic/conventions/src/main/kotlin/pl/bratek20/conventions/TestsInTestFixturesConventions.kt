package pl.bratek20.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.named
import pl.bratek20.extensions.sourceSets
import pl.bratek20.extensions.versionCatalog

class TestsInTestFixturesConventions : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            dependencies {
                add("testFixturesImplementation", versionCatalog().findLibrary("junit-jupiter-api").get())
                add("testFixturesImplementation", versionCatalog().findLibrary("junit-jupiter-params").get())
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

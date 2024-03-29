package pl.bratek20.conventions.internal

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.net.URI

class RepositoriesConventions : Plugin<Project> {
    override fun apply(project: Project) {
        with(project.repositories) {
            mavenCentral()

            mavenLocal()

            if (System.getenv("GITHUB_ACTOR") != null) {
                maven {
                    name = "GitHubPackages"
                    url = URI.create("https://maven.pkg.github.com/bratek20/starter")
                    credentials {
                        username = System.getenv("GITHUB_ACTOR")
                        password = System.getenv("GITHUB_TOKEN")
                    }
                }
            }
        }
    }
}
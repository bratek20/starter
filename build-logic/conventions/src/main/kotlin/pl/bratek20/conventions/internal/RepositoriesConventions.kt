package pl.bratek20.conventions.internal

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.net.URI

class RepositoriesConventions : Plugin<Project> {
    override fun apply(project: Project) {
        with(project.repositories) {
            mavenCentral()

            mavenLocal()

            val githubActor: String? = project.findProperty("githubActor") as String? ?: System.getenv("GITHUB_ACTOR")
            val githubToken: String? = project.findProperty("githubToken") as String? ?: System.getenv("GITHUB_TOKEN")

            if (githubActor != null && githubToken != null) {
                maven {
                    name = "GitHubPackages"
                    url = URI.create("https://maven.pkg.github.com/bratek20/starter")
                    credentials {
                        username = githubActor
                        password = githubToken
                    }
                }
            }
        }
    }
}
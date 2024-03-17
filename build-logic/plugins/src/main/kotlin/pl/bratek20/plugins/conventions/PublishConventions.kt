package pl.bratek20.plugins.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.create

class PublishConventions : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            plugins.apply("maven-publish")

            val x = extensions.findByType<PublishingExtension>()
            x?.publications?.create<MavenPublication>("maven") {}
            extensions.configure(PublishingExtension::class.java) { publishing: PublishingExtension ->
                publishing.publications {
                    create("myPublication", MavenPublication::class.java) { publication ->
                        publication.from(project.components.getByName("java"))
                    }
                }
                publishing.repositories {
                    mavenLocal()
                    if (System.getenv("GITHUB_ACTOR") != null && System.getenv("GITHUB_TOKEN") != null) {
                        maven {
                            name = "GitHubPackages"
                            url = project.uri("https://maven.pkg.github.com/bratek20/commons")
                            credentials {
                                username = System.getenv("GITHUB_ACTOR")
                                password = System.getenv("GITHUB_TOKEN")
                            }
                        }
                    }
                }
            }
        }
    }
}

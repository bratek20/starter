package pl.bratek20.plugins.conventions;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPublication;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;

import java.net.URI;

public class PublishConventions implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getPluginManager().apply(MavenPublishPlugin.class);

        project.getExtensions().configure(PublishingExtension.class, publishing -> {
            publishing.publications(publications -> {
                publications.create("myPublication", MavenPublication.class, publication -> {
                    publication.from(project.getComponents().getByName("java"));
                });
            });
            publishing.repositories(repositories -> {
                repositories.mavenLocal();
                if (System.getenv("GITHUB_ACTOR") != null && System.getenv("GITHUB_TOKEN") != null) {
                    repositories.maven(maven -> {
                        maven.setName("GitHubPackages");
                        maven.setUrl(project.uri("https://maven.pkg.github.com/bratek20/commons"));
                        maven.credentials(credentials -> {
                            credentials.setUsername(System.getenv("GITHUB_ACTOR"));
                            credentials.setPassword(System.getenv("GITHUB_TOKEN"));
                        });
                    });
                }
            });
        });
    }

}

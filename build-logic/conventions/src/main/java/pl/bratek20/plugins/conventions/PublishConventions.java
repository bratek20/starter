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

                var username = System.getenv("GITHUB_ACTOR");
                var password = System.getenv("GITHUB_TOKEN");
                project.getLogger().warn("Username: {}, password: {}", username, password);

                if (username != null && password != null) {
                    repositories.maven(maven -> {
                        maven.setName("GitHubPackages");
                        maven.setUrl(project.uri("https://maven.pkg.github.com/bratek20/starter"));
                        maven.credentials(credentials -> {
                            credentials.setUsername(username);
                            credentials.setPassword(password);
                        });
                    });
                }
            });
        });
    }

}

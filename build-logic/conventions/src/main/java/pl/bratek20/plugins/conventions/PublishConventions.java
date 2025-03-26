package pl.bratek20.conventions;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.repositories.PasswordCredentials;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPublication;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;

import java.net.URI;
import java.util.Objects;

public class PublishConventions implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getPluginManager().apply(MavenPublishPlugin.class);

        project.getExtensions().getByType(JavaPluginExtension.class).withSourcesJar();

        project.getExtensions().configure(PublishingExtension.class, publishing -> {
            publishing.publications(publications -> {
                publications.create("myPublication", MavenPublication.class, publication -> {
                    publication.from(project.getComponents().getByName("java"));

                    project.afterEvaluate(proj -> {
                        String groupId = publication.getGroupId() == null ? project.getGroup().toString() : publication.getGroupId();
                        String artifactId = publication.getArtifactId();
                        String version = publication.getVersion() == null ? project.getVersion().toString() : publication.getVersion();
                        proj.getLogger().lifecycle("Publishes artifact: " + groupId + ":" + artifactId + ":" + version);
                    });
                });
            });
            publishing.repositories(repositories -> {
                repositories.mavenLocal();

                var username = System.getenv("GITHUB_ACTOR");
                var password = System.getenv("GITHUB_TOKEN");

                if (username == null && project.hasProperty("githubActor")) {
                    username = Objects.requireNonNull(project.property("githubActor")).toString();
                }
                if (password == null && project.hasProperty("githubToken")) {
                    password = Objects.requireNonNull(project.property("githubToken")).toString();
                }

                if (username != null && password != null) {
                    String finalUsername = username;
                    String finalPassword = password;

                    repositories.maven(maven -> {
                        maven.setName("GitHubPackages");
                        maven.setUrl(project.uri("https://maven.pkg.github.com/bratek20/starter"));
                        maven.credentials(credentials -> {
                            credentials.setUsername(finalUsername);
                            credentials.setPassword(finalPassword);
                        });
                    });
                }

                if (project.hasProperty("artifactoryUsername")) {
                    repositories.maven(maven -> {
                        maven.setName("central");
                        maven.setUrl(project.uri("https://artifactory.devs.tensquaregames.com/artifactory/libs-release-local"));
                        maven.credentials(PasswordCredentials.class, credentials -> {
                            credentials.setUsername(Objects.requireNonNull(project.property("artifactoryUsername")).toString());
                            credentials.setPassword(Objects.requireNonNull(project.property("artifactoryPassword")).toString());
                        });
                    });
                }
            });
        });
    }
}

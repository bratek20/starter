rootProject.name = "examples"

include("simple-app")
include("spring-app")
include("spring-web-app")
include("tests-in-test-fixtures")

val catalogVersion = "1.0.55"

pluginManagement {
    includeBuild("../bratek20-plugins")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from("com.github.bratek20:version-catalog:$catalogVersion")
        }
    }

    repositories {
        mavenLocal()
        mavenCentral()

        val githubActor: String? = if (extra.has("githubActor")) extra["githubActor"] as String else System.getenv("GITHUB_ACTOR")
        val githubToken: String? = if (extra.has("githubToken")) extra["githubToken"] as String else System.getenv("GITHUB_TOKEN")

        if (githubActor != null && githubToken != null) {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/bratek20/starter")
                credentials {
                    username = githubActor
                    password = githubToken
                }
            }
        }
    }
}
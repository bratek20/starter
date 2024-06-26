pluginManagement {
    includeBuild("build-logic")
    includeBuild("bratek20-logs")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from("com.github.bratek20:version-catalog:1.0.24")
        }
    }

    repositories {
        mavenLocal()

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

rootProject.name = "bratek20-starter"

include("bratek20-architecture")
include("bratek20-infrastructure")
include("bratek20-spring")

//include("bratek20-utils")

// "Legacy" modules

//include("bratek20-tests")
//include("bratek20-commons")
//include("spring-disabled")
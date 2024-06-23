pluginManagement {
    includeBuild("build-logic")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from("com.github.bratek20:version-catalog:1.0.15")
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
include("bratek20-utils")

// "Legacy" modules
include("bratek20-spring")
//include("bratek20-tests")
//include("bratek20-commons")
include("bratek20-logs")
include("bratek20-logs:logs-core")
findProject(":bratek20-logs:logs-core")?.name = "logs-core"
include("bratek20-logs:logs-logback")
findProject(":bratek20-logs:logs-logback")?.name = "logs-logback"
include("bratek20-logs:logs-log4j2")
findProject(":bratek20-logs:logs-log4j2")?.name = "logs-log4j2"

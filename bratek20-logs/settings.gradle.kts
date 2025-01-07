pluginManagement {
    includeBuild("../build-logic")
}

val b20Version = "1.0.93"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from("com.github.bratek20:version-catalog:$b20Version")
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

rootProject.name = "bratek20-logs"

include(":logs-core")
include(":logs-slf4j")
include(":logs-logback")
include(":logs-log4j2")
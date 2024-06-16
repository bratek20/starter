pluginManagement {
    includeBuild("build-logic")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from("pl.bratek20:version-catalog:1.0.6")
        }
    }

    repositories {
        mavenLocal()

        if (System.getenv("GITHUB_ACTOR") != null) {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/bratek20/starter")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}

rootProject.name = "bratek20-starter"

include("bratek20-architecture")
include("bratek20-utils")

// "Legacy" modules
//include("bratek20-spring")
//include("bratek20-tests")
//include("bratek20-commons")


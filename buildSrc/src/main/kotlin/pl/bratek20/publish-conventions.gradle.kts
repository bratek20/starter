package pl.bratek20

plugins {
    `maven-publish`
}

publishing {
    publications {
        create<MavenPublication>("myPublication") {
            from(components["java"])
        }
    }
    repositories {
        mavenLocal()

        when {
            System.getenv("GITHUB_ACTOR") != null && System.getenv("GITHUB_TOKEN") != null -> {
                maven {
                    name = "GitHubPackages"
                    url = uri("https://maven.pkg.github.com/bratek20/commons")
                    credentials {
                        username = System.getenv("GITHUB_ACTOR")
                        password = System.getenv("GITHUB_TOKEN")
                    }
                }
            }
        }
    }
}

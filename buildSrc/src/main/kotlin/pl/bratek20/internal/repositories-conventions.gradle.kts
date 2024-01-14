package pl.bratek20.internal

repositories {
    mavenCentral()

    mavenLocal()

    if (System.getenv("GITHUB_ACTOR") != null) {
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

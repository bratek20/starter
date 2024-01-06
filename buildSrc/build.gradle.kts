plugins {
    `kotlin-dsl`

    `maven-publish`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("io.freefair.gradle:lombok-plugin:8.3")
}

publishing {
    publications {
        create<MavenPublication>("pluginMaven") {
            groupId = "pl.bratek20.plugins"
            artifactId = "plugins-conventions"
            version = "1.0.4"
        }
    }
    repositories {
        mavenLocal()

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

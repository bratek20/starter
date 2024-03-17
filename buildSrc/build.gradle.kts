plugins {
    `kotlin-dsl`

    `maven-publish`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(libs.lombok.plugin)
    implementation(libs.spring.boot.plugin)
    implementation(libs.spring.dependency.management.plugin)
}



java {
    sourceCompatibility = JavaVersion.VERSION_17
}

group = "pl.bratek20"
version = "1.0.6"
publishing {
    publications {
        create<MavenPublication>("pluginMaven") {
            artifactId = "bratek20-conventions"
        }
    }
    repositories {
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
}

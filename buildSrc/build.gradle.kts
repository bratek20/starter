plugins {
    `kotlin-dsl`

    `maven-publish`
}

group = "pl.bratek20"
version = "1.0.4"

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("io.freefair.gradle:lombok-plugin:8.3")
}

publishing {
    publications {
        create<MavenPublication>("myPublication") {
            from(components["kotlin"])
        }
    }
    repositories {
        mavenLocal()
    }
}

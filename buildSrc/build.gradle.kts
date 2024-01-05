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
            groupId = "pl.bratek20"
            artifactId = "buildSrc"
            version = "1.0.4"
        }
    }
    repositories {
        mavenLocal()
    }
}

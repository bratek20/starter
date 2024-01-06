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
    }
}

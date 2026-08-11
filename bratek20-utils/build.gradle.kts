plugins {
    id("com.github.bratek20.plugins.b20-library")
    id("com.github.bratek20.plugins.b20-publish")
}

version = "1.2.1"

dependencies {
    implementation(project(":bratek20-architecture"))
    testImplementation(testFixtures(project(":bratek20-architecture")))
}
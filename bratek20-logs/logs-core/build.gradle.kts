plugins {
    id("com.github.bratek20.plugins.b20-library")
}

dependencies {
    implementation(project(":bratek20-architecture"))
    testImplementation(testFixtures(project(":bratek20-architecture")))
}
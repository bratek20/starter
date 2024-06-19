plugins {
    id("com.github.bratek20.kotlin-library-conventions")
}

dependencies {
    implementation(project(":bratek20-architecture"))
    testImplementation(testFixtures(project(":bratek20-architecture")))

    // logs
    implementation(libs.slf4j.api)
    testImplementation(libs.logback.classic)

    testImplementation(libs.logcaptor)
}
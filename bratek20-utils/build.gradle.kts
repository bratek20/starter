plugins {
    id("pl.bratek20.library-conventions")
}

dependencies {
    implementation(project(":bratek20-architecture"))
    testImplementation(testFixtures(project(":bratek20-architecture")))

    // logs
    implementation(libs.log4j.api)
    implementation(libs.log4j.core)
}


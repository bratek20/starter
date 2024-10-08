plugins {
    id("com.github.bratek20.kotlin-library-conventions")
    id("com.github.bratek20.kotest-conventions")
}

dependencies {
    api(project(":logs-core"))
    testFixturesApi(testFixtures(project(":logs-core")))

    implementation(libs.bratek20.architecture)
    testImplementation(testFixtures(libs.bratek20.architecture))

    // logs
    implementation(libs.slf4j.api)
    testImplementation(libs.logback.classic)

    testImplementation(libs.logcaptor)
}
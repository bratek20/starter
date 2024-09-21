plugins {
    id("com.github.bratek20.kotlin-library-conventions")
}

dependencies {
    api(project(":logs-slf4j"))
    testFixturesApi(testFixtures(project(":logs-slf4j")))

    implementation(libs.bratek20.architecture)

    api(libs.logback.classic)

    testImplementation(testFixtures(libs.bratek20.architecture))
}
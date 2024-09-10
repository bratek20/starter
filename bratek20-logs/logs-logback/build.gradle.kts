plugins {
    id("com.github.bratek20.kotlin-library-conventions")
}

dependencies {
    api(project(":logs-slf4j"))
    testFixturesApi(testFixtures(project(":logs-slf4j")))

    api(libs.logback.classic)

    testImplementation(testFixtures(libs.bratek20.architecture))
}
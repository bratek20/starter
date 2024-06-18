plugins {
    id("com.github.bratek20.library-conventions")
}

dependencies {
    api(project(":bratek20-logs:logs-core"))
    testFixturesApi(testFixtures(project(":bratek20-logs:logs-core")))

    api(libs.logback.classic)
}
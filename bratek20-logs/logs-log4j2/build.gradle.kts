plugins {
    id("com.github.bratek20.kotlin-library-conventions")
}

dependencies {
    api(project(":logs-core"))
    testFixturesApi(testFixtures(project(":logs-core")))

    api(libs.logback.classic)
}
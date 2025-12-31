plugins {
    id("com.github.bratek20.plugins.b20-library")
}

group = "com.github.bratek20.logs"
version = "1.1.0"

subprojects {
    group = rootProject.group
    version = rootProject.version
}

dependencies {
    // default setup if consumer does not care about more details
    api(project(":bratek20-logs:logs-logback"))
    testFixturesApi(testFixtures(project(":bratek20-logs:logs-logback")))
}
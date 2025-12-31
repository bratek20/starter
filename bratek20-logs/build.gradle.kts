plugins {
    id("com.github.bratek20.plugins.b20-library")
    id("com.github.bratek20.plugins.b20-publish")
}

val logsGroup = "com.github.bratek20.logs"
val logsVersion = "1.1.0"

group = logsGroup
version = logsVersion

subprojects {
    group = logsGroup
    version = logsVersion
}

dependencies {
    // default setup if consumer does not care about more details
    api(project(":bratek20-logs:logs-logback"))
    testFixturesApi(testFixtures(project(":bratek20-logs:logs-logback")))
}
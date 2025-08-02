plugins {
    id("com.github.bratek20.kotlin-library-conventions")
}

group = "com.github.bratek20.logs"
version = "1.0.34"

subprojects {
    group = rootProject.group
    version = rootProject.version
}

dependencies {
    // default setup if consumer does not care about more details
    api(project(":logs-logback"))
    testFixturesApi(testFixtures(project(":logs-logback")))
}
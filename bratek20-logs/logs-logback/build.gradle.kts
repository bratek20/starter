plugins {
    id("com.github.bratek20.plugins.b20-library")
}

dependencies {
    api(project(":bratek20-logs:logs-slf4j"))
    testFixturesApi(testFixtures(project(":bratek20-logs:logs-slf4j")))

    implementation(project(":bratek20-architecture"))

    api("ch.qos.logback:logback-classic")

    testImplementation(testFixtures(project(":bratek20-architecture")))
}
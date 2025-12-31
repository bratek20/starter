plugins {
    id("com.github.bratek20.plugins.b20-library")
}

dependencies {
    api(project(":bratek20-logs:logs-core"))
    testFixturesApi(testFixtures(project(":bratek20-logs:logs-core")))

    implementation(project(":bratek20-architecture"))
    testImplementation(testFixtures(project(":bratek20-architecture")))

    // logs
    implementation("org.slf4j:slf4j-api")
    testImplementation("ch.qos.logback:logback-classic")

    testImplementation("io.github.hakky54:logcaptor:2.9.2")
}
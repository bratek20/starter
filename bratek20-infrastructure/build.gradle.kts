plugins {
    id("com.github.bratek20.plugins.b20-library")
    id("com.github.bratek20.plugins.b20-publish")
    kotlin("plugin.spring")
}

version = "1.1.2"

dependencies {
    api(project(":bratek20-architecture"))
    testFixturesImplementation(testFixtures(project(":bratek20-architecture")))
    testApi(testFixtures(project(":bratek20-architecture")))

    implementation(project(":bratek20-logs:logs-core"))
    testFixturesImplementation(testFixtures(project(":bratek20-logs")))

    //http client
    implementation("org.springframework:spring-web")
    compileOnly("jakarta.servlet:jakarta.servlet-api")

    //http server
    testFixturesImplementation(project(":bratek20-spring"))

    //implementation(libs.spring.context)
    implementation("org.springframework:spring-context")
}
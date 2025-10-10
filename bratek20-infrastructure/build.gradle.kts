plugins {
    id("com.github.bratek20.kotlin-library-conventions")
    kotlin("plugin.spring") version "1.9.23" // this plugin is needed to allow proper proxing of kotlin classes
}

version = "1.0.58"

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.5.6"))

    api(project(":bratek20-architecture"))
    testFixturesImplementation(testFixtures(project(":bratek20-architecture")))
    testApi(testFixtures(project(":bratek20-architecture")))

    implementation(libs.bratek20.logs.core)
    testFixturesImplementation(testFixtures(libs.bratek20.logs))

    //http client
    implementation("org.springframework:spring-web")
    compileOnly("jakarta.servlet:jakarta.servlet-api")

    //http server
    testFixturesImplementation(project(":bratek20-spring"))

    //implementation(libs.spring.context)
    implementation("org.springframework:spring-context")
}
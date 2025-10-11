plugins {
    id("com.github.bratek20.kotlin-library-conventions")
    kotlin("plugin.spring") version "1.9.23" // this plugin is needed to allow proper proxing of kotlin classes
}

version = "1.0.60"

dependencies {
    api(project(":bratek20-architecture"))
    testFixturesImplementation(testFixtures(project(":bratek20-architecture")))
    testApi(testFixtures(project(":bratek20-architecture")))

    implementation(libs.bratek20.logs.core)
    testFixturesImplementation(testFixtures(libs.bratek20.logs))

    //http client
    implementation(libs.spring.web)
    //servlet-api = { group = "javax.servlet", name = "javax.servlet-api", version = "4.0.1" }
    implementation("javax.servlet:javax.servlet-api:4.0.1") //TODO check if compatible version and move to version catalog

    //http server
    testFixturesImplementation(project(":bratek20-spring"))

    implementation(libs.spring.context)
}
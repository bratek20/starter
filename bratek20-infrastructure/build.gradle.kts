plugins {
    id("com.github.bratek20.kotlin-library-conventions")
}

version = "1.0.51"

dependencies {
    api(project(":bratek20-architecture"))
    testApi(testFixtures(project(":bratek20-architecture")))

    implementation(libs.bratek20.logs.core)
    testFixturesImplementation(testFixtures(libs.bratek20.logs))

    //http client
    implementation(libs.spring.web)
    //servlet-api = { group = "javax.servlet", name = "javax.servlet-api", version = "4.0.1" }
    implementation("javax.servlet:javax.servlet-api:4.0.1") //TODO check if compatible version and move to version catalog

    testImplementation(libs.wiremock)

    //http server
    testFixturesImplementation(project(":bratek20-spring"))
}
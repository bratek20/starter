plugins {
    id("com.github.bratek20.kotlin-library-conventions")
}

dependencies {
    api(project(":bratek20-architecture"))
    testApi(testFixtures(project(":bratek20-architecture")))

    implementation(libs.bratek20.logs.core)
    testFixturesImplementation(testFixtures(libs.bratek20.logs))

    //http client
    implementation(libs.spring.web)

    testImplementation(libs.wiremock)

    //http server
    testFixturesImplementation(project(":bratek20-spring"))
}
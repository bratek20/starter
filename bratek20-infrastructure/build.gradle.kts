plugins {
    id("com.github.bratek20.kotlin-library-conventions")
}

dependencies {
    api(project(":bratek20-architecture"))
    testApi(testFixtures(project(":bratek20-architecture")))

    //http client
    implementation(libs.spring.web)

    testImplementation(libs.wiremock)

    //http server
    testFixturesImplementation(project(":bratek20-spring"))
}
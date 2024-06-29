plugins {
    id("com.github.bratek20.kotlin-library-conventions")
}

dependencies {
    implementation(project(":bratek20-architecture"))
    testImplementation(testFixtures(project(":bratek20-architecture")))

    //http client
    implementation(platform(libs.spring.boot.dependencies))
    implementation("org.springframework:spring-web")

    testImplementation(libs.wiremock)

    //http server
    testFixturesImplementation(project(":bratek20-spring"))
}
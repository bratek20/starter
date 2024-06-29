plugins {
    id("com.github.bratek20.library-conventions")
}

dependencies {
    implementation(project(":bratek20-spring"))
    implementation(project(":bratek20-architecture"))

    testFixturesImplementation(project(":bratek20-spring"))

    testImplementation(testFixtures(project(":bratek20-spring")))
    testImplementation(testFixtures(project(":bratek20-architecture")))

    // scripts
    //TODO api -> implementation
    api(libs.commons.cli)

    // logging
//    implementation(libs.log4j.api)
//    implementation(libs.log4j.core)
//    implementation(libs.log4j.slf4j.impl)

    testImplementation(libs.rest.assured)

    //http
    testImplementation(libs.wiremock)

    implementation(libs.jackson.databind)
}


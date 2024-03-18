plugins {
    id("pl.bratek20.library-conventions")
}

group = "pl.bratek20"
version = "1.0.6"

val log4jVersion = "2.21.0"
val restAssuredVersion = "5.3.0"

dependencies {
    implementation(project(":bratek20-spring"))

    testFixturesImplementation(project(":bratek20-spring"))

    testImplementation(testFixtures(project(":bratek20-spring")))
    testImplementation(testFixtures(project(":bratek20-architecture")))

    // scripts
    api("commons-cli:commons-cli:1.5.0")

    // logging
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:$log4jVersion")

    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")

    //http
    testImplementation("com.github.tomakehurst:wiremock:3.0.0")
}


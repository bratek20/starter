plugins {
    id("pl.bratek20.library-conventions")
}

group = "pl.bratek20"
version = "1.0.6"

dependencies {
    implementation(project(":bratek20-spring"))
    testImplementation(testFixtures(project(":bratek20-spring")))

    // scripts
    api("commons-cli:commons-cli:1.5.0")

    // logging
    implementation("org.apache.logging.log4j:log4j-api:2.21.0")
    implementation("org.apache.logging.log4j:log4j-core:2.21.0")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.21.0")



    testImplementation("io.rest-assured:rest-assured:5.3.0")
}


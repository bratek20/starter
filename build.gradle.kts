plugins {
    id("pl.bratek20.library-conventions")
    id("pl.bratek20.spring-conventions")
}

group = "pl.bratek20"
version = "1.0.5"

dependencies {
    implementation(project(":spring"))
    testImplementation(testFixtures(project(":spring")))

    // scripts
    api("commons-cli:commons-cli:1.5.0")

    // logging
    implementation("org.apache.logging.log4j:log4j-api:2.21.0")
    implementation("org.apache.logging.log4j:log4j-core:2.21.0")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.21.0")

    //event bus
    implementation("com.google.guava:guava:32.1.2-jre")

    implementation("org.springframework:spring-web")

    testImplementation("io.rest-assured:rest-assured:5.3.0")
}


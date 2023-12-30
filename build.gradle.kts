plugins {
    `java-library`

    id("commons.base-conventions")
}

group = "pl.bratek20"
version = "1.0.3"

dependencies {
    // scripts
    api("commons-cli:commons-cli:1.5.0")

    // logging
    implementation("org.apache.logging.log4j:log4j-api:2.21.0")
    implementation("org.apache.logging.log4j:log4j-core:2.21.0")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.21.0")

    // testing
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
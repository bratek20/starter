package pl.bratek20

plugins {
    java

    id("io.freefair.lombok")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

dependencies {
    // testing
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // assertions
    testImplementation("org.assertj:assertj-core:3.24.2")
}

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

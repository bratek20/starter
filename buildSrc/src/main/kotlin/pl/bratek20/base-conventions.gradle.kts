package pl.bratek20

plugins {
    id("pl.bratek20.internal.repositories-conventions")

    java

    id("io.freefair.lombok")

    id("pl.bratek20.spring-library-conventions")
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

tasks.test {
    useJUnitPlatform()
}

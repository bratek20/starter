plugins {
    id("com.github.bratek20.plugins.b20-library")
    id("com.github.bratek20.plugins.b20-publish")
}

version = "1.1.1"

b20Library {
    testsInTestFixtures = true
}

val guavaVersion = "32.1.2-jre"
val guiceVersion = "7.0.0"
val guiceMultibindingsVersion = "4.2.3"

dependencies {
    //event bus
    implementation("com.google.guava:guava:$guavaVersion")

    //spring context
    implementation("org.springframework:spring-context")

    //guice context
    implementation("com.google.inject:guice:$guiceVersion")
    implementation("com.google.inject.extensions:guice-multibindings:$guiceMultibindingsVersion")

    //properties
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    //serialization
    implementation("com.fasterxml.jackson.core:jackson-databind")
}
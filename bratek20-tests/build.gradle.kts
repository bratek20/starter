plugins {
    id("java")
}

group = "pl.bratek20"
version = "1.0.0"

repositories {
    mavenCentral()
}

val junitVersion = "5.9.1"

dependencies {
    implementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
}
plugins {
    id("pl.bratek20.base-conventions")

    id("pl.bratek20.publish-conventions")

    `java-library`
}

group = "pl.bratek20"
version = "1.0.0"

val junitVersion = "5.9.1"

dependencies {
    implementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
}
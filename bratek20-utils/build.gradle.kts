plugins {
    id("com.github.bratek20.kotlin-library-conventions")
    id("com.github.bratek20.kotest-conventions")
}

version = "1.0.50"

dependencies {
    implementation(project(":bratek20-architecture"))
    testImplementation(testFixtures(project(":bratek20-architecture")))
}
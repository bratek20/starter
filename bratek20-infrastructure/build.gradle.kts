plugins {
    id("com.github.bratek20.kotlin-library-conventions")
}

dependencies {
    implementation(project(":bratek20-architecture"))
    testImplementation(testFixtures(project(":bratek20-architecture")))
}
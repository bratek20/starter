plugins {
    id("com.github.bratek20.kotlin-library-conventions")
    id("com.github.bratek20.kotest-conventions")
}

dependencies {
    implementation(libs.bratek20.architecture)
    testImplementation(testFixtures(libs.bratek20.architecture))
}
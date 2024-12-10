plugins {
    id("com.github.bratek20.kotlin-library-conventions")
    id("com.github.bratek20.tests-in-test-fixtures-conventions")
}

version = "1.0.62"

dependencies {
    //event bus
    implementation(libs.guava)

    //spring context
    implementation(libs.spring.context)

    //guice context
    implementation(libs.guice)
    implementation(libs.guice.multibindings)

    //properties
    implementation(libs.jackson.dataformat.yaml)
    implementation(libs.jackson.module.kotlin)

    //serialization
    implementation(libs.jackson.databind)
}
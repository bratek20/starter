plugins {
    id("com.github.bratek20.kotlin-library-conventions")
}

dependencies {
    //event bus
    implementation(libs.guava)

    //spring context
    implementation(platform(libs.spring.boot.dependencies))
    implementation("org.springframework:spring-context")

    //guice
    implementation(libs.guice)
    implementation(libs.guice.multibindings)

    //properties
    implementation(libs.jackson.dataformat.yaml)
    implementation(libs.jackson.module.kotlin)

    //serialization
    implementation(libs.jackson.databind)
}
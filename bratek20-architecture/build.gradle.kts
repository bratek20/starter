plugins {
    id("pl.bratek20.library-conventions")
}

dependencies {
    //event bus
    implementation(libs.guava)

    //spring context
    implementation(platform(libs.spring.boot.dependencies))
    implementation("org.springframework:spring-context")

    //guice
    implementation("com.google.inject:guice:7.0.0")
    implementation("com.google.inject.extensions:guice-multibindings:4.2.3")


    //properties
    implementation(libs.jackson.dataformat.yaml)
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.0")

    testImplementation(project(":bratek20-tests"))

    testFixturesApi(project(":bratek20-tests"))
}
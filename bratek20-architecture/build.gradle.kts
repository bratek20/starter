plugins {
    id("pl.bratek20.library-conventions")
}

dependencies {
    //event bus
    implementation(libs.guava)

    //spring context
    implementation(platform(libs.spring.boot.dependencies))
    implementation("org.springframework:spring-context")

    //properties
    implementation(libs.jackson.dataformat.yaml)

    testImplementation(project(":bratek20-tests"))

    testFixturesApi(project(":bratek20-tests"))
}
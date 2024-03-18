plugins {
    id("pl.bratek20.library-conventions")
}

group = "pl.bratek20"
version = "1.0.0"

dependencies {
    //event bus
    implementation(libs.guava)
    implementation(project(":bratek20-spring"))

    //properties
    implementation(libs.jackson.dataformat.yaml)

    testImplementation(project(":bratek20-tests"))
    testImplementation(project(":bratek20-spring"))

    testFixturesImplementation(project(":bratek20-spring"))

    testFixturesApi(project(":bratek20-tests"))
}
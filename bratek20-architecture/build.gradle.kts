plugins {
    id("pl.bratek20.library-conventions")
}

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
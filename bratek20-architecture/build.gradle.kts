plugins {
    id("pl.bratek20.library-conventions")
}

group = "pl.bratek20"
version = "1.0.0"

val guavaVersion = "32.1.2-jre"

dependencies {
    //event bus
    implementation("com.google.guava:guava:$guavaVersion")
    implementation(project(":bratek20-spring"))

    testImplementation(project(":bratek20-tests"))
    testImplementation(project(":bratek20-spring"))

    testFixturesImplementation(project(":bratek20-tests"))

}
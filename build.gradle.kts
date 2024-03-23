plugins {
    id("pl.bratek20.library-conventions")
}

group = "pl.bratek20"
version = "1.0.0-SNAPSHOT"

subprojects {
    group = "pl.bratek20"
    version = "1.0.0-SNAPSHOT"
}

dependencies {
    api(project(":bratek20-architecture"))
    testFixturesApi(testFixtures(project(":bratek20-architecture")))

    api(project(":bratek20-commons"))
    testFixturesApi(testFixtures(project(":bratek20-commons")))

    api(project(":bratek20-spring"))
    testFixturesApi(testFixtures(project(":bratek20-spring")))

    testFixturesApi(project(":bratek20-tests"))
}
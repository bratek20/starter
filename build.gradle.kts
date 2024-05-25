plugins {
    id("pl.bratek20.library-conventions")
}

val groupAll = "pl.bratek20"
val versionAll = "1.0.9"

group = groupAll
version = versionAll

subprojects {
    group = groupAll
    version = versionAll
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
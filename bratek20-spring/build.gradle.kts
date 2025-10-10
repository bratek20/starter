plugins {
    id("com.github.bratek20.kotlin-library-conventions")
    kotlin("plugin.spring") version "1.9.23"
}

version = "1.0.53"

val springBootVersion: String by project

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))

    api(project(":bratek20-infrastructure"))
    testImplementation(testFixtures(project(":bratek20-architecture")))

    implementation("org.springframework.boot:spring-boot-starter-web")
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-web")

    implementation(libs.bratek20.logs.core)

    // web testing
    testImplementation("io.rest-assured:rest-assured")

    //mongo
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    testFixturesImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testFixturesImplementation("org.testcontainers:junit-jupiter")
    testFixturesImplementation("org.testcontainers:mongodb")
}
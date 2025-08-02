plugins {
    id("com.github.bratek20.kotlin-library-conventions")
    kotlin("plugin.spring") version "1.9.23"
}

version = "1.0.50"

dependencies {
    api(project(":bratek20-infrastructure"))

    implementation(platform(libs.spring.boot.dependencies))
    implementation("org.springframework.boot:spring-boot-starter-web")
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-web")

    implementation(libs.bratek20.logs.core)

    // web testing
    testImplementation(libs.rest.assured)

    //old, might be useful in the future
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    //
    //api("org.springframework.boot:spring-boot-starter-data-jdbc")
    //testFixturesImplementation("org.springframework.boot:spring-boot-starter-data-jdbc")
}
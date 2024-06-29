plugins {
    id("com.github.bratek20.kotlin-library-conventions")
}

dependencies {
    implementation(project(":bratek20-architecture"))
    testImplementation(testFixtures(project(":bratek20-architecture")))

    implementation(project(":bratek20-infrastructure"))

    implementation(platform(libs.spring.boot.dependencies))

    implementation("org.springframework.boot:spring-boot-starter-web")
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-web")

    // web testing
    testImplementation(libs.rest.assured)

    //old, might be useful in the future
    //testFixturesImplementation("org.springframework.boot:spring-boot-starter-test")
    //
    //api("org.springframework.boot:spring-boot-starter-data-jdbc")
    //testFixturesImplementation("org.springframework.boot:spring-boot-starter-data-jdbc")
}
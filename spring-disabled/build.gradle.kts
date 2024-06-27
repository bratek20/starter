plugins {
    id("com.github.bratek20.library-conventions")
}

dependencies {
    implementation(project(":bratek20-architecture"))
    testImplementation(testFixtures(project(":bratek20-architecture")))

    api(platform(libs.spring.boot.dependencies))

    // context
    api("org.springframework:spring-context")

    //web
    api("org.springframework:spring-web")

    implementation("org.springframework.boot:spring-boot-starter-web")
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-web")

    testFixturesImplementation("org.springframework.boot:spring-boot-starter-test")

    api("org.springframework.boot:spring-boot-starter-data-jdbc")
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-data-jdbc")

    implementation("org.flywaydb:flyway-mysql")

    // data runtimes
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("com.h2database:h2")

    // testcontainers
    testFixturesImplementation(libs.testcontainers)
    testFixturesImplementation(libs.testcontainers.junit.jupiter)
    testFixturesImplementation(libs.testcontainers.mysql)

    // swagger
    implementation(libs.spring.boot.swagger)

    // web testing
    testImplementation("io.rest-assured:rest-assured")

    // linking
    implementation(project(":bratek20-spring"))
}
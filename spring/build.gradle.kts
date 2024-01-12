plugins {
    id("pl.bratek20.library-conventions")

    id("pl.bratek20.spring-conventions")
}

group = "pl.bratek20.spring"
version = "1.0.0"

dependencies {
    api("org.springframework:spring-context")

    implementation("org.springframework.boot:spring-boot-starter-web")
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-web")

    testFixturesImplementation("org.springframework.boot:spring-boot-starter-test")

    api("org.springframework.boot:spring-boot-starter-data-jpa")
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.flywaydb:flyway-mysql")

    // testcontainers
    testFixturesImplementation("org.testcontainers:testcontainers")
    testFixturesImplementation("org.testcontainers:junit-jupiter")
    testFixturesImplementation("org.testcontainers:mysql")
}
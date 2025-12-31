plugins {
    id("com.github.bratek20.plugins.b20-library")
    kotlin("plugin.spring")
}

version = "1.1.1"

dependencies {
    api(project(":bratek20-infrastructure"))
    testImplementation(testFixtures(project(":bratek20-architecture")))

    implementation("org.springframework.boot:spring-boot-starter-web")
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-web")

    implementation(project(":bratek20-logs:logs-core"))

    // web testing
    testImplementation("io.rest-assured:rest-assured")

    //mongo
    compileOnly("org.springframework.boot:spring-boot-starter-data-mongodb")
    testImplementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    testFixturesImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testFixturesImplementation("org.testcontainers:junit-jupiter")
    testFixturesImplementation("org.testcontainers:mongodb")
}
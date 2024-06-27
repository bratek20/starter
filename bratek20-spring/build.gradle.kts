plugins {
    id("com.github.bratek20.library-conventions")
}

//TODO clean those dependencies
//TODO migrate to com.github.bratek20 package
//TODO double check api dependencies
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

    // web testing
    testImplementation("io.rest-assured:rest-assured")
}
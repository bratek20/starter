
plugins {
    id("pl.bratek20.library-conventions")
    id("pl.bratek20.spring-conventions")
}

group = "pl.bratek20"
version = "1.0.5"

dependencies {
    // scripts
    api("commons-cli:commons-cli:1.5.0")

    // logging
    implementation("org.apache.logging.log4j:log4j-api:2.21.0")
    implementation("org.apache.logging.log4j:log4j-core:2.21.0")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.21.0")

    //event bus
    implementation("com.google.guava:guava:32.1.2-jre")

    // dependency injection
    //implementation("org.springframework:spring-context:6.1.2")

    //spring web
    //implementation("org.springframework:spring-web:6.1.2")

    testImplementation("io.rest-assured:rest-assured:5.3.0")

    implementation("org.springframework.boot:spring-boot-starter-web")
}


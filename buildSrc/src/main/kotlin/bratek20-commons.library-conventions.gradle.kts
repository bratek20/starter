plugins {
    id("bratek20-commons.base-conventions")

    `java-library`

    `java-test-fixtures`
}

dependencies {
    // dependency injection
    testFixturesImplementation("org.springframework:spring-context:6.1.2")

    // assertions
    testFixturesImplementation("org.assertj:assertj-core:3.24.2")

    // testing
    testFixturesImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
}

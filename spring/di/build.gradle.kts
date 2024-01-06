plugins {
    id("pl.bratek20.library-conventions")

    id("pl.bratek20.spring-conventions")
}

group = "pl.bratek20.spring"
version = "1.0.0"

dependencies {
    api("org.springframework:spring-context")
}
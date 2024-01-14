package pl.bratek20

plugins {
    id("pl.bratek20.spring-library-conventions")
}

tasks.named("bootJar").configure {
    enabled = true
}
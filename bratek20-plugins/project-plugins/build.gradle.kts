version = "1.1.2"

val kotlinVersion = "1.9.25"
val shadowVersion = "8.1.1"

dependencies {
    implementation(project(":settings-plugins"))

    implementation("org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin:$kotlinVersion")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:${common.versions.springBoot.get()}")
    implementation("org.jetbrains.kotlin.plugin.spring:org.jetbrains.kotlin.plugin.spring.gradle.plugin:${kotlinVersion}")
    implementation("com.github.johnrengelman:shadow:$shadowVersion")
}

gradlePlugin {
    plugins {
        create("b20-publish") {
            id = "com.github.bratek20.plugins.b20-publish"
            implementationClass = "com.github.bratek20.plugins.B20Publish"
        }

        create("b20-library") {
            id = "com.github.bratek20.plugins.b20-library"
            implementationClass = "com.github.bratek20.plugins.B20Library"
        }

        create("b20-simple-app") {
            id = "com.github.bratek20.plugins.b20-simple-app"
            implementationClass = "com.github.bratek20.plugins.B20SimpleApp"
        }

        create("b20-spring-app") {
            id = "com.github.bratek20.plugins.b20-spring-app"
            implementationClass = "com.github.bratek20.plugins.B20SpringApp"
        }
    }
}


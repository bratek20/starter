import com.github.bratek20.plugins.B20PublishComponent

plugins {
    `java-platform`
    id("com.github.bratek20.plugins.b20-publish")
}

version = "0.0.6"

b20Publish {
    component = B20PublishComponent.JAVA_PLATFORM
}

javaPlatform {
    // We want to depend on another BOM (ie. SpringBoot BOM),
    // so we must allow regular dependencies in this platform.
    allowDependencies()
}

val springBootVersion = "3.5.6"

dependencies {
    api(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))
}



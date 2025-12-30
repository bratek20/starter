plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

subprojects {
    group = "com.github.bratek20.plugins"

    pluginManager.apply("org.gradle.kotlin.kotlin-dsl")
    pluginManager.apply("java-gradle-plugin")
    pluginManager.apply("maven-publish")

    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    extensions.configure<PublishingExtension> {
        repositories {
            maven {
                name = "b20-starter"
                url = uri("https://maven.pkg.github.com/bratek20/starter")
                credentials {
                    username = providers.gradleProperty("githubActor").orNull
                    password = providers.gradleProperty("githubToken").orNull
                }
            }
        }
    }
}
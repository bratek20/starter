import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    `maven-publish`
}

dependencies {
    implementation(libs.kotlin.jvm.plugin)

    implementation(libs.lombok.plugin)
    implementation(libs.spring.boot.plugin)
}

java {
    toolchain {
        version = libs.versions.java.get()
    }
}

tasks.withType(KotlinCompile::class.java) {
    kotlinOptions {
        jvmTarget = libs.versions.java.get()
    }
}

gradlePlugin {
    plugins {
        register("repositories-conventions") {
            id = "pl.bratek20.internal.repositories-conventions"
            implementationClass = "pl.bratek20.conventions.internal.RepositoriesConventions"
        }
        register("base-conventions") {
            id = "pl.bratek20.base-conventions"
            implementationClass = "pl.bratek20.conventions.BaseConventions"
        }
        register("simple-library-conventions") {
            id = "pl.bratek20.simple-library-conventions"
            implementationClass = "pl.bratek20.conventions.SimpleLibraryConventions"
        }
        register("library-conventions") {
            id = "pl.bratek20.library-conventions"
            implementationClass = "pl.bratek20.conventions.LibraryConventions"
        }
        register("publish-conventions") {
            id = "pl.bratek20.publish-conventions"
            implementationClass = "pl.bratek20.conventions.PublishConventions"
        }
        register("spring-app-conventions") {
            id = "pl.bratek20.spring-app-conventions"
            implementationClass = "pl.bratek20.conventions.SpringAppConventions"
        }
    }
}

version = "1.0.0-SNAPSHOT"
publishing {
    repositories {
        mavenLocal()

        if (System.getenv("GITHUB_ACTOR") != null) {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/bratek20/starter")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}

repositories {
    mavenCentral()
}
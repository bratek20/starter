plugins {
    `kotlin-dsl`
    `maven-publish`
}

dependencies {
    implementation(libs.lombok.plugin)
    implementation(libs.spring.dependency.management.plugin)
    implementation(libs.spring.boot.plugin)
}

java {
    toolchain {
        version = libs.versions.java
    }
}

gradlePlugin {
    plugins {
        register("repositories-conventions") {
            id = "pl.bratek20.internal.repositories-conventions"
            implementationClass = "pl.bratek20.plugins.conventions.internal.RepositoriesConventions"
        }
        register("base-conventions") {
            id = "pl.bratek20.base-conventions"
            implementationClass = "pl.bratek20.plugins.conventions.BaseConventions"
        }
        register("simple-library-conventions") {
            id = "pl.bratek20.simple-library-conventions"
            implementationClass = "pl.bratek20.plugins.conventions.SimpleLibraryConventions"
        }
        register("library-conventions") {
            id = "pl.bratek20.library-conventions"
            implementationClass = "pl.bratek20.plugins.conventions.LibraryConventions"
        }
        register("publish-conventions") {
            id = "pl.bratek20.publish-conventions"
            implementationClass = "pl.bratek20.plugins.conventions.PublishConventions"
        }
        register("spring-library-conventions") {
            id = "pl.bratek20.spring-library-conventions"
            implementationClass = "pl.bratek20.plugins.conventions.SpringLibraryConventions"
        }
        register("spring-app-conventions") {
            id = "pl.bratek20.spring-app-conventions"
            implementationClass = "pl.bratek20.plugins.conventions.SpringAppConventions"
        }
    }
}

version = "1.1.0"
publishing {
    repositories {
        mavenLocal()

        if (System.getenv("GITHUB_ACTOR") != null) {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/bratek20/commons")
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
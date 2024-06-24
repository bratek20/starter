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
            id = "com.github.bratek20.internal.repositories-conventions"
            implementationClass = "pl.bratek20.conventions.internal.RepositoriesConventions"
        }
        register("base-conventions") {
            id = "com.github.bratek20.base-conventions"
            implementationClass = "pl.bratek20.conventions.BaseConventions"
        }
        register("simple-library-conventions") {
            id = "com.github.bratek20.simple-library-conventions"
            implementationClass = "pl.bratek20.conventions.SimpleLibraryConventions"
        }
        register("library-conventions") {
            id = "com.github.bratek20.library-conventions"
            implementationClass = "pl.bratek20.conventions.LibraryConventions"
        }
        register("publish-conventions") {
            id = "com.github.bratek20.publish-conventions"
            implementationClass = "pl.bratek20.conventions.PublishConventions"
        }
        register("spring-app-conventions") {
            id = "com.github.bratek20.spring-app-conventions"
            implementationClass = "pl.bratek20.conventions.SpringAppConventions"
        }
        register("kotlin-conventions") {
            id = "com.github.bratek20.kotlin-conventions"
            implementationClass = "pl.bratek20.conventions.KotlinConventions"
        }
        register("kotlin-library-conventions") {
            id = "com.github.bratek20.kotlin-library-conventions"
            implementationClass = "pl.bratek20.conventions.KotlinLibraryConventions"
        }
        register("internal-kotlin-library-conventions") {
            id = "com.github.bratek20.internal-kotlin-library-conventions"
            implementationClass = "pl.bratek20.conventions.InternalKotlinLibraryConventions"
        }
        register("java-conventions") {
            id = "com.github.bratek20.java-conventions"
            implementationClass = "pl.bratek20.conventions.JavaConventions"
        }
    }
}

version = "1.0.1"
publishing {
    repositories {
        mavenLocal()

        if (project.hasProperty("githubActor") && project.hasProperty("githubToken")) {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/bratek20/starter")
                credentials {
                    username = project.findProperty("githubActor") as String
                    password = project.findProperty("githubToken") as String
                }
            }
        }

        if (project.hasProperty("centralUsername")) {
            maven {
                name = "central"
                url = uri("https://artifactory.devs.tensquaregames.com/artifactory/plugins-release-local")
                credentials {
                    username = project.findProperty("centralUsername") as String
                    password = project.findProperty("centralPassword") as String
                }
            }
        }
    }
}

repositories {
    mavenCentral()
}
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

version = "1.0.7"

plugins {
    `kotlin-dsl`
    `maven-publish`
}

dependencies {
    implementation(libs.kotlin.jvm.plugin)

    implementation(libs.lombok.plugin)
    implementation(libs.spring.boot.plugin)

    implementation("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get()))
    }
}

tasks.withType(KotlinCompile::class.java) {
    kotlinOptions {
        jvmTarget = libs.versions.java.get()
    }
}

gradlePlugin {
    plugins {
        register("kotlin-conventions") {
            id = "com.github.bratek20.kotlin-conventions"
            implementationClass = "pl.bratek20.conventions.KotlinConventions"
        }
        register("java-conventions") {
            id = "com.github.bratek20.java-conventions"
            implementationClass = "pl.bratek20.conventions.JavaConventions"
        }
        register("kotlin-library-conventions") {
            id = "com.github.bratek20.kotlin-library-conventions"
            implementationClass = "pl.bratek20.conventions.KotlinLibraryConventions"
        }
        register("internal-kotlin-library-conventions") {
            id = "com.github.bratek20.internal-kotlin-library-conventions"
            implementationClass = "pl.bratek20.conventions.InternalKotlinLibraryConventions"
        }
        register("kotest-conventions") {
            id = "com.github.bratek20.kotest-conventions"
            implementationClass = "pl.bratek20.conventions.KotestConventions"
        }
        register("spring-web-app-conventions") {
            id = "com.github.bratek20.spring-web-app-conventions"
            implementationClass = "pl.bratek20.conventions.SpringWebAppConventions"
        }
        register("tests-in-test-fixtures-conventions") {
            id = "com.github.bratek20.tests-in-test-fixtures-conventions"
            implementationClass = "pl.bratek20.conventions.TestsInTestFixturesConventions"
        }
    }
}


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

        if (project.hasProperty("artifactoryUsername") && project.hasProperty("artifactoryPassword")) {
            maven {
                name = "central"
                url = uri("https://artifactory.devs.tensquaregames.com/artifactory/plugins-release-local")
                credentials {
                    username = project.findProperty("artifactoryUsername") as String
                    password = project.findProperty("artifactoryPassword") as String
                }
            }
        }
    }
}
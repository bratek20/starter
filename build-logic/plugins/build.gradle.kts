import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
//    implementation(libs.android.gradle.plugin)
//    implementation(libs.kotlin.gradle.plugin)
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

//group = "pl.bratek20"
//version = "1.0.6"
//publishing {
//    publications {
//        create<MavenPublication>("pluginMaven") {
//            artifactId = "bratek20-conventions"
//        }
//    }
//    repositories {
//        mavenLocal()
//
//        if (System.getenv("GITHUB_ACTOR") != null) {
//            maven {
//                name = "GitHubPackages"
//                url = uri("https://maven.pkg.github.com/bratek20/commons")
//                credentials {
//                    username = System.getenv("GITHUB_ACTOR")
//                    password = System.getenv("GITHUB_TOKEN")
//                }
//            }
//        }
//    }
//}
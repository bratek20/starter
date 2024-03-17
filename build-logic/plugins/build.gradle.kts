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
            id = "pl.bratek20.repositories-conventions"
            implementationClass = "pl.bratek20.plugins.conventions.internal.RepositoriesConventions"
        }
    }
}
//        register("androidAppCompose") {
//            id = "conventionPluginsApp.android.app.compose"
//            implementationClass = "plugins.AndroidAppComposeConventionPlugin"
//        }
//        register("androidLibCompose") {
//            id = "conventionPluginsApp.android.lib.compose"
//            implementationClass = "plugins.AndroidLibComposeConventionPlugin"
//        }
//        register("androidLib") {
//            id = "conventionPluginsApp.android.library"
//            implementationClass = "plugins.AndroidLibComposeConventionPlugin"
//        }
//    }
//}
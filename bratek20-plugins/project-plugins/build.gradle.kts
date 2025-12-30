version = "0.1.0"

dependencies {
    implementation(project(":settings-plugins"))
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

        create("b20-app") {
            id = "com.github.bratek20.plugins.b20-app"
            implementationClass = "com.github.bratek20.plugins.B20App"
        }
    }
}


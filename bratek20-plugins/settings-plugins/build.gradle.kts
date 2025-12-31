version = "0.1.0"

gradlePlugin {
    plugins {
        create("b20-settings") {
            id = "com.github.bratek20.plugins.b20-settings"
            implementationClass = "com.github.bratek20.plugins.B20Settings"
        }
    }
}

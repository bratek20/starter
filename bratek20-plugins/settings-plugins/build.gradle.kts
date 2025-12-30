version = "0.1.0"

gradlePlugin {
    plugins {
        create("b20-settings") {
            id = "com.github.bratek20.plugins.woh-settings"
            implementationClass = "com.github.bratek20.plugins.WohSettings"
        }
    }
}

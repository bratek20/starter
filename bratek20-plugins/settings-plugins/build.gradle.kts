version = "2.0.0"

val foojayResolverVersion = "1.0.0"

dependencies {
    //So B20Settings can register the toolchain resolver on behalf of consumers,
    //instead of every repo declaring the foojay plugin itself.
    implementation("org.gradle.toolchains:foojay-resolver:$foojayResolverVersion")
}

gradlePlugin {
    plugins {
        create("b20-settings") {
            id = "com.github.bratek20.plugins.b20-settings"
            implementationClass = "com.github.bratek20.plugins.B20Settings"
        }
    }
}

plugins {
    //id("com.github.bratek20.spring-web-app-conventions")
    alias(libs.plugins.bratek20.internal.kotlin.library.conventions)
}

dependencies {
    implementation(libs.bratek20.spring)
    //implementation("com.github.bratek20.logs:bratek20-logs")
}
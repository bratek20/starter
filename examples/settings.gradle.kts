rootProject.name = "examples"

//include("simple-app")
//include("spring-app")
//include("spring-web-app")
include("tests-in-test-fixtures")

pluginManagement {
    includeBuild("../bratek20-plugins")
}

plugins {
    id("com.github.bratek20.plugins.b20-settings")
}

b20Settings {
    catalogVersion = "1.1.2"
}
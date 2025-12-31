pluginManagement {
    //includeBuild("build-logic")
    includeBuild("bratek20-plugins")
}
includeBuild("bratek20-logs")
includeBuild("examples")

plugins {
    id("com.github.bratek20.plugins.b20-settings")
}

rootProject.name = "bratek20-starter"

include("bratek20-bom")
include("bratek20-architecture")
//include("bratek20-infrastructure")
//include("bratek20-spring")
//include("bratek20-utils")


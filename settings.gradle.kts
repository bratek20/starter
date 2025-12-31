pluginManagement {
    includeBuild("bratek20-plugins")
}
//includeBuild("examples")

plugins {
    id("com.github.bratek20.plugins.b20-settings")
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

dependencyResolutionManagement {
    versionCatalogs {
        create("common") {
            from(files("gradle/common.versions.toml"))
        }
    }
}

rootProject.name = "bratek20-starter"

include("bratek20-catalog")
include("bratek20-bom")

include("bratek20-architecture")

include("bratek20-logs")
include("bratek20-logs:logs-core")
findProject(":bratek20-logs:logs-core")?.name = "logs-core"
include("bratek20-logs:logs-slf4j")
findProject(":bratek20-logs:logs-slf4j")?.name = "logs-slf4j"
include("bratek20-logs:logs-logback")
findProject(":bratek20-logs:logs-logback")?.name = "logs-logback"

include("bratek20-infrastructure")
include("bratek20-spring")
include("bratek20-utils")


pluginManagement {
    includeBuild("build-logic")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from("pl.bratek20:version-catalog:1.0.0-SNAPSHOT")
        }
    }

    repositories {
        mavenLocal()
    }
}

rootProject.name = "bratek20-starter"

include("bratek20-spring")
include("bratek20-tests")
include("bratek20-architecture")
include("bratek20-commons")
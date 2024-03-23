pluginManagement {
    includeBuild("../version-catalog")
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

rootProject.name = "build-logic"
include(":conventions")
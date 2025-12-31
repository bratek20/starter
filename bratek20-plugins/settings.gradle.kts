dependencyResolutionManagement {
    versionCatalogs {
        create("common") {
            from(files("../gradle/common.versions.toml"))
        }
    }
}

include("settings-plugins")
include("project-plugins")
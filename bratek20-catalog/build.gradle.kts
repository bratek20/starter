import com.github.bratek20.plugins.B20PublishComponent

plugins {
    `version-catalog`
    id("com.github.bratek20.plugins.b20-publish")
}

//changed by script
val catalogVersion = "1.1.7"

//changed manually
val b20ArchVersion = "1.1.1"
val b20InfraVersion = "1.1.2"
val b20SpringVersion = "1.1.1"
val b20UtilsVersion = "1.1.0"

val b20LogsVersion = "1.1.0"
val b20PluginsVersion = "1.1.2"
val b20BomVersion = "1.1.0"

catalog {
    versionCatalog {
        //bratek20 dependencies
        library("bratek20-architecture", "com.github.bratek20", "bratek20-architecture").version(b20ArchVersion)
        library("bratek20-infrastructure", "com.github.bratek20", "bratek20-infrastructure").version(b20InfraVersion)
        library("bratek20-spring", "com.github.bratek20", "bratek20-spring").version(b20SpringVersion)
        library("bratek20-utils", "com.github.bratek20", "bratek20-utils").version(b20UtilsVersion)
        library("bratek20-bom", "com.github.bratek20", "bratek20-bom").version(b20BomVersion)

        library("bratek20-logs", "com.github.bratek20.logs", "bratek20-logs").version(b20LogsVersion)
        library("bratek20-logs-core", "com.github.bratek20.logs", "logs-core").version(b20LogsVersion)
        library("bratek20-logs-slf4j", "com.github.bratek20.logs", "logs-slf4j").version(b20LogsVersion)
        library("bratek20-logs-logback", "com.github.bratek20.logs", "logs-logback").version(b20LogsVersion)

        //bratek20 plugins
        plugin("b20-publish", "com.github.bratek20.plugins.b20-publish").version(b20PluginsVersion)
        plugin("b20-library", "com.github.bratek20.plugins.b20-library").version(b20PluginsVersion)
        plugin("b20-simple-app", "com.github.bratek20.plugins.b20-simple-app").version(b20PluginsVersion)
        plugin("b20-spring-app", "com.github.bratek20.plugins.b20-spring-app").version(b20PluginsVersion)
    }
}

b20Publish {
    component = B20PublishComponent.VERSION_CATALOG
    version = catalogVersion
    artifactId = "version-catalog"
}
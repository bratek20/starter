plugins {
    `version-catalog`
    `maven-publish`
}

val catalogVersion = "1.0.10"
val bratek20StarterVersion = "1.0.7"
val bratek20PluginsVersion = "1.0.1"

catalog {
    versionCatalog {
        version("java", "17")
        version("kotlin", "1.9.23")

        version("junit", "5.9.1")
        version("assertj", "3.24.2")
        version("kotest", "5.9.1")

        version("testcontainers", "1.16.2")

        version("lombok", "8.3")
        version("spring-boot", "3.0.13")
        version("protobuf", "3.25.2")

        version("slf4j", "2.0.13")
        version("log4j", "2.21.0")
        version("logback", "1.5.6")

        version("rest-assured", "5.3.0")
        version("jackson", "2.17.0")
        version("guice", "7.0.0")
        version("guice-multibindings", "4.2.3")

        version("bratek20-starter", bratek20StarterVersion)
        version("bratek20-plugins", bratek20PluginsVersion)

        //plugins
        library("kotlin-jvm-plugin", "org.jetbrains.kotlin.jvm", "org.jetbrains.kotlin.jvm.gradle.plugin").versionRef("kotlin")

        library("lombok-plugin", "io.freefair.gradle", "lombok-plugin").versionRef("lombok")
        library("spring-boot-plugin", "org.springframework.boot", "spring-boot-gradle-plugin").versionRef("spring-boot")

        //impl dependencies
        library("spring-boot-dependencies", "org.springframework.boot", "spring-boot-dependencies").versionRef("spring-boot")
        library("spring-boot-swagger", "org.springdoc", "springdoc-openapi-starter-webmvc-ui").version("2.2.0")

        library("guava", "com.google.guava", "guava").version("32.1.2-jre")
        library("jackson-dataformat-yaml", "com.fasterxml.jackson.dataformat", "jackson-dataformat-yaml").versionRef("jackson")
        library("jackson-databind", "com.fasterxml.jackson.core", "jackson-databind").versionRef("jackson")
        library("jackson-module-kotlin", "com.fasterxml.jackson.module", "jackson-module-kotlin").versionRef("jackson")

        library("guice", "com.google.inject", "guice").versionRef("guice")
        library("guice-multibindings", "com.google.inject.extensions", "guice-multibindings").versionRef("guice-multibindings")

        library("protobuf-protoc", "com.google.protobuf", "protoc").versionRef("protobuf")
        library("protobuf-java", "com.google.protobuf", "protobuf-java").versionRef("protobuf")

        library("slf4j-api", "org.slf4j", "slf4j-api").versionRef("slf4j")
        library("logback-classic", "ch.qos.logback", "logback-classic").versionRef("logback")

        library("log4j-core", "org.apache.logging.log4j", "log4j-core").versionRef("log4j")
        library("log4j-slf4j-impl", "org.apache.logging.log4j", "log4j-slf4j-impl").versionRef("log4j")

        library("logcaptor", "io.github.hakky54", "logcaptor").version("2.9.2")

        library("commons-cli", "commons-cli", "commons-cli").version("1.5.0")

        //test dependencies
        library("kotest-runner-junit5", "io.kotest", "kotest-runner-junit5").versionRef("kotest")
        library("kotest-assertions-core", "io.kotest", "kotest-assertions-core").versionRef("kotest")

        library("junit-jupiter-api", "org.junit.jupiter", "junit-jupiter-api").versionRef("junit")
        library("junit-jupiter-params", "org.junit.jupiter", "junit-jupiter-params").versionRef("junit")
        library("junit-jupiter-engine", "org.junit.jupiter", "junit-jupiter-engine").versionRef("junit")

        library("assertj-core", "org.assertj", "assertj-core").versionRef("assertj")

        library("testcontainers", "org.testcontainers", "testcontainers").versionRef("testcontainers")
        library("testcontainers-junit-jupiter", "org.testcontainers", "junit-jupiter").versionRef("testcontainers")
        library("testcontainers-mysql", "org.testcontainers", "mysql").versionRef("testcontainers")

        library("wiremock", "com.github.tomakehurst", "wiremock").version("3.0.0")
        library("rest-assured", "io.rest-assured", "rest-assured").version("5.3.0")

        //bratek20 dependencies
        library("bratek20-starter", "com.github.bratek20", "bratek20-starter").versionRef("bratek20-starter")
        library("bratek20-architecture", "com.github.bratek20", "bratek20-architecture").versionRef("bratek20-starter")
        library("break20-logs-core", "com.github.bratek20.logs", "logs-core").versionRef("bratek20-starter")
        library("bratek20-logs-logback", "com.github.bratek20.logs", "logs-logback").versionRef("bratek20-starter")
        library("bratek20-logs-log4j2", "com.github.bratek20.logs", "logs-log4j2").versionRef("bratek20-starter")
        library("bratek20-utils", "com.github.bratek20", "bratek20-utils").versionRef("bratek20-starter")

        //plugins
        plugin("protobuf", "com.google.protobuf").version("0.9.4")
        plugin("jib", "com.google.cloud.tools.jib").version("3.3.1")

        //bratek20 plugins
        plugin("bratek20-kotlin-library-conventions", "com.github.bratek20.kotlin-library-conventions").versionRef("bratek20-plugins")
        plugin("bratek20-kotlin-conventions", "com.github.bratek20.kotlin-conventions").versionRef("bratek20-plugins")
        plugin("bratek20-internal-kotlin-library-conventions", "com.github.bratek20.internal-kotlin-library-conventions").versionRef("bratek20-plugins")
        plugin("bratek20-java-conventions", "com.github.bratek20.java-conventions").versionRef("bratek20-plugins")

        plugin("bratek20-base-conventions", "com.github.bratek20.base-conventions").versionRef("bratek20-plugins")
        plugin("bratek20-spring-app-conventions", "com.github.bratek20.spring-app-conventions").versionRef("bratek20-plugins")
        plugin("bratek20-library-conventions", "com.github.bratek20.library-conventions").versionRef("bratek20-plugins")
        plugin("bratek20-simple-library-conventions", "com.github.bratek20.simple-library-conventions").versionRef("bratek20-plugins")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["versionCatalog"])

            groupId = "com.github.bratek20"
            artifactId = "version-catalog"
            version = catalogVersion
        }
    }

    repositories {
        mavenLocal()

        if (System.getenv("GITHUB_ACTOR") != null) {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/bratek20/starter")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }

        if (project.hasProperty("centralUsername")) {
            maven {
                name = "central"
                url = uri("https://artifactory.devs.tensquaregames.com/artifactory/libs-release-local")
                credentials {
                    username = project.findProperty("centralUsername") as String
                    password = project.findProperty("centralPassword") as String
                }
            }
        }
    }
}
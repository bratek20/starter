plugins {
    `version-catalog`
    `maven-publish`
}

catalog {
    // declare the aliases, bundles and versions in this block
    versionCatalog {
        version("java", "17")
        version("kotlin", "1.9.23")

        version("lombok", "8.3")
        version("junit", "5.9.1")
        version("spring-boot", "3.2.4")
        version("assertj", "3.24.2")
        version("protobuf", "3.25.2")

        version("bratek20", "1.0.0-SNAPSHOT")

        //plugins
        library("kotlin-jvm-plugin", "org.jetbrains.kotlin.jvm", "org.jetbrains.kotlin.jvm.gradle.plugin").versionRef("kotlin")

        library("lombok-plugin", "io.freefair.gradle", "lombok-plugin").versionRef("lombok")
        library("spring-boot-plugin", "org.springframework.boot", "spring-boot-gradle-plugin").versionRef("spring-boot")
        library("spring-boot-dependencies", "org.springframework.boot", "spring-boot-dependencies").versionRef("spring-boot")

        //impl dependencies
        library("guava", "com.google.guava", "guava").version("32.1.2-jre")
        library("jackson-dataformat-yaml", "com.fasterxml.jackson.dataformat", "jackson-dataformat-yaml").version("2.17.0")

        library("protobuf-protoc", "com.google.protobuf", "protoc").versionRef("protobuf")
        library("protobuf-java", "com.google.protobuf", "protobuf-java").versionRef("protobuf")

        //test dependencies
        library("junit-jupiter-api", "org.junit.jupiter", "junit-jupiter-api").versionRef("junit")
        library("junit-jupiter-engine", "org.junit.jupiter", "junit-jupiter-engine").versionRef("junit")

        library("assertj-core", "org.assertj", "assertj-core").versionRef("assertj")

        //bratek20 dependencies
        library("bratek20-starter", "pl.bratek20", "bratek20-starter").versionRef("bratek20")

        plugin("protobuf", "com.google.protobuf").version("0.9.4")

        plugin("bratek20-base-conventions", "pl.bratek20.base-conventions").versionRef("bratek20")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["versionCatalog"])

            groupId = "pl.bratek20"
            artifactId = "version-catalog"
            version = "1.0.0-SNAPSHOT"
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
    }
}
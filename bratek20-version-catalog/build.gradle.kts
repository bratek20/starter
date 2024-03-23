plugins {
    `version-catalog`
    `maven-publish`
}

catalog {
    // declare the aliases, bundles and versions in this block
    versionCatalog {
        version("java", "17")
        version("lombok", "8.3")
        version("junit", "5.9.1")
        version("spring-boot", "3.1.3")
        version("assertj", "3.24.2")

        library("lombok-plugin", "io.freefair.gradle", "lombok-plugin").versionRef("lombok")
        library("spring-boot-plugin", "org.springframework.boot", "spring-boot-gradle-plugin").versionRef("spring-boot")
        library("spring-dependency-management-plugin", "io.spring.gradle", "dependency-management-plugin").version("1.1.3")

        library("junit-jupiter-api", "org.junit.jupiter", "junit-jupiter-api").versionRef("junit")
        library("junit-jupiter-engine", "org.junit.jupiter", "junit-jupiter-engine").versionRef("junit")

        library("assertj-core", "org.assertj", "assertj-core").versionRef("assertj")

        library("guava", "com.google.guava", "guava").version("32.1.2-jre")
        library("jackson-dataformat-yaml", "com.fasterxml.jackson.dataformat", "jackson-dataformat-yaml").version("2.17.0")
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
    }
}
package com.github.bratek20.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.withType

open class B20TestExtension {
    var printTestLogs: Boolean = true
}

class B20Test : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            val ext = extensions.create<B20TestExtension>("b20Test")

            with(dependencies) {
                add("testImplementation", "org.junit.jupiter:junit-jupiter-api")
                add("testImplementation", "org.junit.jupiter:junit-jupiter-params")
                add("testImplementation", "org.junit.platform:junit-platform-launcher")

                add("testRuntimeOnly", "org.junit.jupiter:junit-jupiter-engine")

                add("testImplementation", "org.assertj:assertj-core")
            }

            tasks.withType<Test>().configureEach {
                useJUnitPlatform()

                if (ext.printTestLogs) {
                    testLogging {
                        showStandardStreams = true
                    }
                }
            }
        }
    }
}

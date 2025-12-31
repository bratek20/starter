package com.github.bratek20.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

class B20App : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(plugins) {
                apply(B20Java::class.java)
                apply(B20Kotlin::class.java)

                apply(B20Test::class.java)

                apply("org.springframework.boot")
                apply("org.jetbrains.kotlin.plugin.spring")
            }

            with(dependencies) {
                val b20BomLib = getB20BomLib(project)
                add("implementation", platform(b20BomLib))
            }
        }
    }
}

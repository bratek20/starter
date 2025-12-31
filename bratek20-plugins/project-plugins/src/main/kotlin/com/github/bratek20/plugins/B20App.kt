package com.github.bratek20.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

open class B20App : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(plugins) {
                apply(B20Java::class.java)
                apply(B20Kotlin::class.java)

                apply(B20Test::class.java)
            }

            with(dependencies) {
                val b20BomLib = getB20BomLib(project)
                add("implementation", platform(b20BomLib))
            }
        }
    }
}


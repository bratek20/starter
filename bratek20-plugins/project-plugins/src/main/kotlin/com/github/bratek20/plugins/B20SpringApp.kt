package com.github.bratek20.plugins

import org.gradle.api.Project

class B20SpringApp: B20App() {
    override fun apply(project: Project) {
        super.apply(project)
        with(project) {
            with(plugins) {
                apply("org.springframework.boot")
                apply("org.jetbrains.kotlin.plugin.spring")
            }

            with(dependencies) {
                add("implementation", b20Catalog().findLibrary("bratek20-spring").get())
            }
        }
    }
}


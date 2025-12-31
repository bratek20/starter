package com.github.bratek20.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project

open class B20LibraryExtension {
    var testsInTestFixtures: Boolean = false
}

fun Project.b20Catalog(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
fun Project.hasB20Catalog(): Boolean =
    extensions.getByType<VersionCatalogsExtension>().find("libs").isPresent

fun getB20BomLib(project: Project): Provider<MinimalExternalModuleDependency> {
    return project.b20Catalog().findLibrary("bratek20-bom").orElseThrow {
        IllegalStateException("bratek20-bom not found in version catalog")
    }
}

class B20Library : Plugin<Project> {
    override fun apply(project: Project) {
        val ext = project.extensions.create<B20LibraryExtension>("b20Library")

        with(project) {
            with(pluginManager) {
                apply("java-library")
                apply("java-test-fixtures")
            }

            with(plugins) {
                apply(B20Java::class.java)
                apply(B20Kotlin::class.java)

                apply(B20Test::class.java)

                afterEvaluate {
                    if (ext.testsInTestFixtures) {
                        plugins.apply(B20TestsInTestFixtures::class.java)
                    }
                }
            }

            with(dependencies) {
                add("testFixturesImplementation", "org.assertj:assertj-core")

                if (hasB20Catalog()) {
                    val b20BomLib = getB20BomLib(project)
                    add("implementation", platform(b20BomLib))
                    add("testFixturesImplementation", platform(b20BomLib))
                }
                else {
                    add("implementation", platform(project(":bratek20-bom")))
                    add("testFixturesImplementation", platform(project(":bratek20-bom")))
                }
            }
        }
    }
}

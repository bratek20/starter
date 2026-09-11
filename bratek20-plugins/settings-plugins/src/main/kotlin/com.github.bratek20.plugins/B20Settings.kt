package com.github.bratek20.plugins

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.create
import org.gradle.toolchains.foojay.FoojayToolchainsConventionPlugin

open class B20SettingsExtension {
    var catalogVersion: String? = null
}

class B20Settings : Plugin<Settings> {
    override fun apply(settings: Settings) {
        val ext = settings.extensions.create<B20SettingsExtension>("b20Settings")

        //Registers the foojay toolchain resolver, so Gradle can provision the JDK a
        //project asks for. Applied here rather than in each consumer's settings file.
        settings.pluginManager.apply(FoojayToolchainsConventionPlugin::class.java)

        settings.gradle.settingsEvaluated {
            settings.dependencyResolutionManagement {
                ext.catalogVersion?.let { catalogVersion ->
                    settings.logger.lifecycle("[B20Settings] Creating b20 version catalog as 'libs' for version: $catalogVersion")
                    versionCatalogs {
                        create("libs") {
                            from("com.github.bratek20:version-catalog:$catalogVersion")
                        }
                    }
                }

                repositories {
                    mavenCentral()
                    mavenLocal()

                    val githubActor = settings.tryResolveProperty(GITHUB_ACTOR)
                    val githubToken = settings.tryResolveProperty(GITHUB_TOKEN)

                    if (githubActor != null && githubToken != null) {
                        maven {
                            name = "b20-starter"
                            url = B20_STARTER_URI
                            credentials {
                                username = githubActor
                                password = githubToken
                            }
                        }
                    }
                }
            }
        }
    }
}

package com.github.bratek20.plugins

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.create
import java.net.URI

open class B20SettingsExtension {
    var catalogVersion: String? = null
}

class B20Settings : Plugin<Settings> {
    override fun apply(settings: Settings) {
        val ext = settings.extensions.create<B20SettingsExtension>("b20Settings")

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

                    val githubActor = settings.findProperty("githubActor")
                    val githubToken = settings.findProperty("githubToken")

                    if (githubActor != null && githubToken != null) {
                        maven {
                            name = "GitHubPackages"
                            url = URI.create("https://maven.pkg.github.com/bratek20/starter")
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

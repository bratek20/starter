package com.github.bratek20.plugins

import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import org.gradle.api.provider.ProviderFactory
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging

fun Project.resolveProperty(name: String): String {
    return resolveProperty(providers, logger, name)
}

fun Project.tryResolveProperty(name: String): String? {
    return tryResolveProperty(providers, logger, name)
}

val Settings.logger: Logger
    get() = Logging.getLogger(this::class.java)

fun Settings.resolveProperty(name: String): String {
    return resolveProperty(providers, logger, name)
}

fun Settings.tryResolveProperty(name: String): String? {
    return tryResolveProperty(providers, logger, name)
}

fun resolveProperty(providers: ProviderFactory, logger: Logger, name: String): String {
    return tryResolveProperty(providers, logger, name)
        ?: error("Property '$name' not found in Gradle properties or environment variables")
}

fun tryResolveProperty(providers: ProviderFactory, logger: Logger, name: String): String? {
    providers.gradleProperty(name).orNull?.let {
        logger.lifecycle("Property '$name' resolved using Gradle properties")
        return it
    }

    val envName = name
        .replace(Regex("([a-z])([A-Z]+)"), "$1_$2")
        .uppercase()

    logger.lifecycle("Gradle property '$name' not found, trying env variable '$envName'")
    return System.getenv(envName)
}
package pl.bratek20.extensions

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.plugins.PluginContainer
import org.gradle.jvm.toolchain.JavaLanguageVersion

fun applyJavaLibraryPlugin(project: Project, plugins: PluginContainer) {
    plugins.apply(JavaLibraryPlugin::class.java)

    applyJavaVersion(project)
}

fun applyJavaPlugin(project: Project, plugins: PluginContainer) {
    plugins.apply(JavaPlugin::class.java)

    applyJavaVersion(project)
}

private fun applyJavaVersion(project: Project) {
    val javaVersion = JavaVersion.toVersion(project.versionCatalog().findVersion("java").get())

    project.extensions.getByType(JavaPluginExtension::class.java)
        .toolchain.languageVersion
        .set(JavaLanguageVersion.of(javaVersion.majorVersion))
}
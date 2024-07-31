package pl.bratek20.extensions

import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer

internal fun Project.sourceSets(): SourceSetContainer = extensions.getByName("sourceSets") as SourceSetContainer

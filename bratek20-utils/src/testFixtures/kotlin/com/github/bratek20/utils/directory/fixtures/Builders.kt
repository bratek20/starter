// DO NOT EDIT! Autogenerated by HLA tool

package com.github.bratek20.utils.directory.fixtures

import com.github.bratek20.utils.directory.api.*

fun fileName(value: String = "someValue"): FileName {
    return FileName(value)
}

fun directoryName(value: String = "someValue"): DirectoryName {
    return DirectoryName(value)
}

fun path(value: String = "someValue"): Path {
    return pathCreate(value)
}

fun fileContent(value: String = "someValue"): FileContent {
    return fileContentCreate(value)
}

data class FileDef(
    var name: String = "someValue",
    var content: String = "someValue",
)
fun file(init: FileDef.() -> Unit = {}): File {
    val def = FileDef().apply(init)
    return File.create(
        name = FileName(def.name),
        content = fileContentCreate(def.content),
    )
}

data class DirectoryDef(
    var name: String = "someValue",
    var files: List<(FileDef.() -> Unit)> = emptyList(),
    var directories: List<(DirectoryDef.() -> Unit)> = emptyList(),
)
fun directory(init: DirectoryDef.() -> Unit = {}): Directory {
    val def = DirectoryDef().apply(init)
    return Directory.create(
        name = DirectoryName(def.name),
        files = def.files.map { it -> file(it) },
        directories = def.directories.map { it -> directory(it) },
    )
}

data class CompareResultDef(
    var same: Boolean = false,
    var differences: List<String> = emptyList(),
)
fun compareResult(init: CompareResultDef.() -> Unit = {}): CompareResult {
    val def = CompareResultDef().apply(init)
    return CompareResult.create(
        same = def.same,
        differences = def.differences,
    )
}
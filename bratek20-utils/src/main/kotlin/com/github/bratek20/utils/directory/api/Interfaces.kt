// DO NOT EDIT! Autogenerated by HLA tool

package com.github.bratek20.utils.directory.api

interface Files {
    fun write(path: Path, file: File): Unit

    @Throws(
        FileNotFoundException::class,
    )
    fun read(path: Path): File

    @Throws(
        FileNotFoundException::class,
    )
    fun delete(path: Path): Unit

    fun compare(file1: File, file2: File): CompareResult
}

interface Directories {
    fun write(path: Path, dir: Directory): Unit

    @Throws(
        DirectoryNotFoundException::class,
    )
    fun read(path: Path): Directory

    @Throws(
        DirectoryNotFoundException::class,
    )
    fun delete(path: Path): Unit

    fun compare(dir1: Directory, dir2: Directory): CompareResult
}
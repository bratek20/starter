package com.github.bratek20.utils

fun pascalToCamelCase(name: String): String {
    return name[0].lowercase() + name.substring(1)
}

fun camelToPascalCase(name: String): String {
    return name[0].uppercase() + name.substring(1)
}

fun camelToScreamingSnakeCase(name: String): String {
    return name.replace(Regex("([a-z0-9])([A-Z])"), "$1_$2").uppercase()
}

fun stringify(name: String): String {
    return "\"$name\""
}

fun destringify(name: String): String {
    if (name.length < 2 || name[0] != '"' || name[name.length - 1] != '"') {
        return name
    }
    return name.substring(1, name.length - 1)
}
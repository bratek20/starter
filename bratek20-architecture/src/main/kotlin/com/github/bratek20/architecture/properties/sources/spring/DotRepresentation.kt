package com.github.bratek20.architecture.properties.sources.spring

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class DotRepresentation(private val map: Map<String, Any>) {
    fun toJsonObject(): String {
        val jsonMap = mutableMapOf<String, Any>()
        map.forEach { (key, value) ->
            insertIntoMap(jsonMap, key, value)
        }
        return jacksonObjectMapper().writeValueAsString(jsonMap)
    }

    private fun insertIntoMap(current: MutableMap<String, Any>, key: String, value: Any) {
        val parts = key.split(".")
        if (parts.size == 1) {
            val listIndex = parts[0].indexOf("[")
            if (listIndex != -1) {
                val listKey = parts[0].substring(0, listIndex)
                val listIndexValue = parts[0].substring(listIndex + 1, parts[0].length - 1).toInt()
                val list = current.getOrPut(listKey) { mutableListOf<Any>() } as MutableList<Any>
                if (list.size <= listIndexValue) {
                    list.addAll(List(listIndexValue - list.size + 1) { "" })
                }
                list[listIndexValue] = value
            } else {
                current[key] = value
            }
        } else {
            val nestedKey = parts[0]
            val nestedMap = current.getOrPut(nestedKey) { mutableMapOf<String, Any>() } as MutableMap<String, Any>
            insertIntoMap(nestedMap, parts.drop(1).joinToString("."), value)
        }
    }
}
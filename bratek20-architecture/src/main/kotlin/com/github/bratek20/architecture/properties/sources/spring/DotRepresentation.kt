package com.github.bratek20.architecture.properties.sources.spring

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class DotRepresentation(private val map: Map<String, Any>) {
    private val mapper = jacksonObjectMapper()
    fun toJsonObject(): String {
        var result: JsonNode = mapper.createObjectNode()
        map.forEach { (key, value) ->
            val keyNode = nodeFor(key, value)
            result = merge(result, keyNode)
        }
        return mapper.writeValueAsString(result)
    }

    private fun merge(node1: JsonNode, node2: JsonNode): JsonNode {
        val result = mapper.createObjectNode()
        node1.fieldNames().forEach { fieldName ->
            result.put(fieldName, node1.get(fieldName))
        }
        node2.fieldNames().forEach { fieldName ->
            result.put(fieldName, node2.get(fieldName))
        }
        return result
    }

    private fun nodeFor(key: String, value: Any): JsonNode {
        if (key.contains("[")) {
            val (listKey, index) = key.split("[")
            val indexValue = index.removeSuffix("]").toInt()
            val list = List<Any?>(indexValue + 1) { null }.toMutableList()
            list[indexValue] = value
            return rootNodeFor(listKey, list)
        }
        return rootNodeFor(key, value)
    }

    private fun rootNodeFor(key: String, value: Any): JsonNode {
        val node = mapper.createObjectNode()
        when (value) {
            is String -> node.put(key, value)
            is Int -> node.put(key, value)
            is Long -> node.put(key, value)
            is Double -> node.put(key, value)
            is Boolean -> node.put(key, value)
            is Float -> node.put(key, value)
            is JsonNode -> node.set<JsonNode>(key, value)
            else -> node.putPOJO(key, value)
        }
        return node
    }
}
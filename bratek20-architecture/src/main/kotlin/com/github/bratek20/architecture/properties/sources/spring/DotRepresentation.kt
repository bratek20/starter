package com.github.bratek20.architecture.properties.sources.spring

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlin.math.max

class DotRepresentation(private val map: Map<String, Any>) {
    private val mapper = jacksonObjectMapper()
    fun toJsonObject(): String {
        var result: JsonNode = mapper.createObjectNode()
        map.forEach { (key, value) ->
            val keyNode = nodeFor(".$key", value)
            result = merge(result, keyNode)
        }
        return mapper.writeValueAsString(result)
    }

    private fun mergeArray(node1: ArrayNode, node2: ArrayNode): ArrayNode {
        val result = mapper.createArrayNode()
        for (i in 0 until max(node1.size(), node2.size())) {
            result.addNull()
        }

        for (i in 0 until result.size()) {
            val node1Element = node1.get(i)
            val node2Element = node2.get(i)

            if (node1Element != null && !node1Element.isNull) {
                result.set(i, node1Element)
            } else if (node2Element != null && !node2Element.isNull) {
                result.set(i, node2Element)
            }
        }
        return result
    }

    private fun merge(node1: JsonNode, node2: JsonNode): JsonNode {
        val result = mapper.createObjectNode()
        putToResult(result, node1, node2)
        putToResult(result, node2, node1)
        return result
    }

    private fun putToResult(result: ObjectNode, node1: JsonNode, node2: JsonNode) {
        node1.fieldNames().forEach { fieldName ->
            val value = node1.get(fieldName)
            if (result.has(fieldName)) {
                return@forEach
            }

            if(value.isArray && node2.has(fieldName)) {
                val arr = mergeArray(node1.get(fieldName) as ArrayNode, node2.get(fieldName) as ArrayNode)
                result.put(fieldName, arr)
            }
            else if (value.isObject && node2.has(fieldName)) {
                val obj = merge(value, node2.get(fieldName))
                result.put(fieldName, obj)
            }
            else {
                result.put(fieldName, value)
            }
        }
    }

    private fun nodeFor(key: String, value: Any): JsonNode {
        if (key.isEmpty()) {
            return valueFor(value)
        }
        if (key.startsWith(".")) {
            val noFirstDot = key.substringAfter(".")
            val separatorIndex = noFirstDot.indexOfAny(charArrayOf('[', '.'))
            if (separatorIndex == -1) {
                val objectNode = mapper.createObjectNode()
                objectNode.putIfAbsent(noFirstDot, valueFor(value))
                return objectNode
            }
            val objectKey = noFirstDot.substring(0, separatorIndex)
            val rest = noFirstDot.substring(separatorIndex)
            val objectNode = mapper.createObjectNode()
            objectNode.putIfAbsent(objectKey, nodeFor(rest, value))
            return objectNode
        }
        if (key.startsWith("[")) {
            val index = key.substringAfter("[").substringBefore("]").toInt()
            val rest = key.substringAfter("]")

            val arrayNode = mapper.createArrayNode()
            for (i in 0 until index + 1) {
                arrayNode.addNull()
            }
            arrayNode.set(index, nodeFor(rest, value))

            return arrayNode
        }
        throw IllegalArgumentException("Invalid key: $key")
    }

    private fun valueFor(value: Any): JsonNode {
        return mapper.valueToTree(value)
    }
}
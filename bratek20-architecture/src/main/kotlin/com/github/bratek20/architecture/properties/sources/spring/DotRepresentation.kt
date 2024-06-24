package com.github.bratek20.architecture.properties.sources.spring

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class DotRepresentation(private val map: Map<String, Any>) {
    private val mapper = jacksonObjectMapper()
    private val manipulator = JsonManipulator()

    fun toJsonObject(): String {
        var result: JsonNode = mapper.createObjectNode()
        map.forEach { (key, value) ->
            val keyNode = nodeFor(".$key", value)
            result = mapper.valueToTree(manipulator.merge(result, keyNode))
        }
        return mapper.writeValueAsString(result)
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
        try {
            return mapper.valueToTree(value)
        } catch (e: Exception) {
            throw IllegalArgumentException("Cannot convert value to json: $value")
        }

    }
}
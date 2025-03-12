package com.github.bratek20.architecture.structs.impl

import com.github.bratek20.architecture.serialization.context.SerializationFactory
import com.github.bratek20.architecture.structs.api.*

class AnyStructHelperLogic: AnyStructHelper {
    override fun getValues(anyStruct: AnyStruct, path: StructPath): List<AnyStructWithPath> {
        return getValuesFor(anyStruct, path, "")
    }

    private fun toStructPath(traversedPath: String): StructPath {
        if (traversedPath.endsWith("/")) {
            return StructPath(traversedPath.dropLast(1))
        }
        return StructPath(traversedPath)
    }

    private fun getValuesFor(anyStruct: AnyStruct, path: StructPath, traversedPath: String): List<AnyStructWithPath> {
        if (path.value.isEmpty()) {
            return listOf(AnyStructWithPath(anyStruct, toStructPath(traversedPath)))
        }

        val parts = path.value.split("/")
        val currentRaw = parts[0]
        val currentTraversed = "$traversedPath$currentRaw/"

        val current = currentRaw.removeSuffix("?")
        val isOptional = currentRaw.endsWith("?")

        val rest = StructPath(parts.subList(1, parts.size).joinToString("/"))

        if(current.startsWith("[")) {
            val list = anyStruct.asList()
            if (current == "[*]") {
                return list.flatMapIndexed { idx, struct ->
                    val currentTraversedWithIdx = currentTraversed.replace("[*]", "[$idx]")
                    getValuesFor(anyToAnyStruct(struct), rest, currentTraversedWithIdx)
                }
            }
            val idx = current.drop(1).dropLast(1).toInt()
            return getValuesFor(anyToAnyStruct(list[idx]), rest, currentTraversed)
        }

        val value = anyStruct.asObject()[current]
        if (value == null) {
            if (isOptional) {
                return listOf()
            }
            throw StructTraversalException("Null detected at '$currentTraversed'")
        }

        if (rest.value.isEmpty()) {
            return listOf(AnyStructWithPath(anyToAnyStruct(value), toStructPath(currentTraversed)))
        }
        return getValuesFor(anyToAnyStruct(value), rest, currentTraversed)
    }

    private fun anyToAnyStruct(any: Any): AnyStruct {
        if (any is List<*>) {
            return serializer.asStructList(any)
        }
        if (any is Map<*, *>) {
            return serializer.asStruct(any)
        }
        return StructPrimitive(any.toString())
    }

    companion object {
        val serializer = SerializationFactory.createSerializer()
    }
}
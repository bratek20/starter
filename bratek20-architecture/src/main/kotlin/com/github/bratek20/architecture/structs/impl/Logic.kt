package com.github.bratek20.architecture.structs.impl

import com.github.bratek20.architecture.serialization.context.SerializationFactory
import com.github.bratek20.architecture.structs.api.AnyStruct
import com.github.bratek20.architecture.structs.api.AnyStructHelper
import com.github.bratek20.architecture.structs.api.StructPath
import com.github.bratek20.architecture.structs.api.StructPrimitive

class AnyStructHelperLogic: AnyStructHelper {
    override fun getValues(anyStruct: AnyStruct, path: StructPath): List<AnyStruct> {
        if (path.value.isEmpty()) {
            return listOf(anyStruct)
        }

        val parts = path.value.split("/")
        val current = parts[0]
        val rest = StructPath(parts.subList(1, parts.size).joinToString("/"))

        if(current.startsWith("[")) {
            val list = anyStruct.asList()
            if (current == "[*]") {
                return list.flatMap { getValues(it, rest) }
            }
            val idx = current.drop(1).dropLast(1).toInt()
            return getValues(list[idx], rest)
        }

        if (rest.value.isEmpty()) {
            return listOf(anyToAnyStruct(anyStruct.asObject()[current]!!))
        }
        return getValues(anyToAnyStruct(anyStruct.asObject()[current]!!), rest)
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
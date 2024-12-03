package com.github.bratek20.architecture.structs.impl

import com.github.bratek20.architecture.structs.api.AnyStruct
import com.github.bratek20.architecture.structs.api.AnyStructHelper
import com.github.bratek20.architecture.structs.api.StructPath
import com.github.bratek20.architecture.structs.api.StructValue

class AnyStructHelperLogic: AnyStructHelper {
    override fun getValues(struct: AnyStruct, path: StructPath): List<StructValue> {
        val parts = path.value.split("/")
        val current = parts[0]
        val rest = StructPath(parts.subList(1, parts.size).joinToString("/"))

        if(current.startsWith("[")) {
            val list = struct.asList()
            if (current == "[*]") {
                return list.flatMap { getValues(it, rest) }
            }
            val idx = current.drop(1).dropLast(1).toInt()
            return getValues(list[idx], rest)
        }
        else {
            return listOf(StructValue(struct.asObject()[path.value].toString()))
        }
    }
}
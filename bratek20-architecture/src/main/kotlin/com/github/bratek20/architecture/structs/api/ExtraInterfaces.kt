package com.github.bratek20.architecture.structs.api

data class AnyStructWithPath(
    val value: AnyStruct,
    val path: StructPath
)

interface AnyStructHelper {
    @Throws(
        StructTraversalException::class,
    )
    fun getValues(struct: AnyStruct, path: StructPath): List<AnyStructWithPath>
}
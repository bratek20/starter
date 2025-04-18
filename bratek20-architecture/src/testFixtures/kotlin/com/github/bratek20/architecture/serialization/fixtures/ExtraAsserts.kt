package com.github.bratek20.architecture.serialization.fixtures

import com.github.bratek20.architecture.serialization.api.*
import com.github.bratek20.architecture.serialization.context.SerializationFactory
import com.github.bratek20.architecture.structs.api.Struct
import com.github.bratek20.architecture.structs.api.SerializableList
import com.github.bratek20.architecture.structs.api.StructList
import com.github.bratek20.architecture.structs.fixtures.ExpectedStruct
import com.github.bratek20.architecture.structs.fixtures.assertStructEquals
import org.assertj.core.api.Assertions.assertThat

fun assertSerializedValueAsStruct(given: SerializedValue, expected: ExpectedStruct) {
    val serializer = SerializationFactory.createSerializer()
    assertStructEquals(serializer.deserialize(given, Struct::class.java), expected)
}

fun assertSerializedValueAsListOfStructs(given: SerializedValue, expected: List<ExpectedStruct>) {
    val serializer = SerializationFactory.createSerializer()
    val actual = serializer.deserialize(given, StructList::class.java)
    assertThat(actual).hasSize(expected.size)
    actual.forEachIndexed { index, struct ->
        assertStructEquals(struct, expected[index])
    }
}
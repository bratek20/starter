package com.github.bratek20.architecture.users

import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.context.stableContextBuilder
import com.github.bratek20.architecture.data.api.DataStorage
import com.github.bratek20.architecture.data.api.ListDataKey
import com.github.bratek20.architecture.data.api.asMapKey
import com.github.bratek20.architecture.data.context.DataInMemoryImpl
import com.github.bratek20.architecture.users.api.UserDataStorage
import com.github.bratek20.architecture.users.impl.UsersInMemoryImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TestData(
    val id: String,
    val value: Int
)

class UsersImplTest {
    @Test
    fun `should work`() {
        val c = stableContextBuilder()
            .withModules(
                DataInMemoryImpl(),
                UsersInMemoryImpl()
            )
            .build()

        val userStorage = c.get(UserDataStorage::class.java)
        val appStorage = c.get(DataStorage::class.java)

        val key = ListDataKey("test_key", TestData::class).asMapKey {
            it.id
        }

        val e = userStorage.getOrCreateElement(key, "e1") {
            TestData(it, 1)
        }

        assertThat(e.id).isEqualTo("e1")
    }

}
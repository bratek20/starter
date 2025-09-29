package com.github.bratek20.architecture.users

import com.github.bratek20.architecture.data.api.DataStorage
import com.github.bratek20.architecture.data.api.ListDataKey
import com.github.bratek20.architecture.data.api.asMapKey
import com.github.bratek20.architecture.users.api.UserDataStorage
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TestData(
    val id: String,
    val value: Int
)

class UsersImplTest {
    @Test
    fun `should work`() {
        val builder = TestUserContextBuilder()
        val c1 = builder.build(1)
        val c2 = builder.build(2)

        val userStorage1 = c1.get(UserDataStorage::class.java)
        val userStorage2 = c2.get(UserDataStorage::class.java)
        val appStorage = builder.appContext.get(DataStorage::class.java)

        val key = ListDataKey("test_key", TestData::class).asMapKey {
            it.id
        }

        userStorage1.getOrCreateElement(key, "e1") {
            TestData(it, 1)
        }
        userStorage2.getOrCreateElement(key, "e2") {
            TestData(it, 2)
        }

        val testUserKeyFor =  { userId: String ->
            ListDataKey("user${userId}.test_key", TestData::class)
        }
        appStorage.get(testUserKeyFor("1")).forEach {
            assertThat(it.value).isEqualTo(1)
        }
        appStorage.get(testUserKeyFor("2")).forEach {
            assertThat(it.value).isEqualTo(2)
        }
    }
}
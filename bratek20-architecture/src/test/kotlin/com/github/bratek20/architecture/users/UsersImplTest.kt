package com.github.bratek20.architecture.users

import com.github.bratek20.architecture.data.api.DataStorage
import com.github.bratek20.architecture.data.api.ListDataKey
import com.github.bratek20.architecture.data.api.asMapKey
import com.github.bratek20.architecture.users.api.UserDataStorage
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TestData(
    val id: String,
    var value: Int
)

class UsersImplTest {
    @Test
    fun `should write to different keys per user + getOrCreate + upsert`() {
        val builder = TestUserContextBuilder()
        val c1 = builder.build(1)
        val c2 = builder.build(2)

        val userStorage1 = c1.get(UserDataStorage::class.java)
        val userStorage2 = c2.get(UserDataStorage::class.java)
        val appStorage = builder.appContext.get(DataStorage::class.java)

        val key = ListDataKey("test_key", TestData::class).asMapKey {
            it.id
        }

        val e1A = userStorage1.getOrCreateElement(key, "e1A") {
            TestData(it, 1)
        }
        userStorage1.getOrCreateElement(key, "e1B") {
            TestData(it, 1)
        }
        e1A.value = 3
        userStorage1.upsertElement(key, e1A)

        userStorage2.getOrCreateElement(key, "e2") {
            TestData(it, 2)
        }

        val testUserKeyFor =  { userId: String ->
            ListDataKey("user${userId}.test_key", TestData::class)
        }

        val list1 = appStorage.get(testUserKeyFor("1"))
        assertThat(list1).hasSize(2)
        assertThat(list1[0].id).isEqualTo("e1A")
        assertThat(list1[0].value).isEqualTo(3)
        assertThat(list1[1].id).isEqualTo("e1B")
        assertThat(list1[1].value).isEqualTo(1)

        appStorage.get(testUserKeyFor("2")).forEach {
            assertThat(it.value).isEqualTo(2)
        }
    }
}
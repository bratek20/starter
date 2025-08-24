package com.github.bratek20.spring.mongo

import com.github.bratek20.architecture.serialization.api.SerializationType
import com.github.bratek20.architecture.serialization.api.SerializedValue
import com.github.bratek20.spring.webapp.SpringWebApp
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.mongodb.core.MongoTemplate
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class MongoDataManipulatorIntegrationTest {
    companion object {
        @Container
        @JvmStatic
        val mongo = MongoDBContainer("mongo:5.0.15")
    }

    private lateinit var manipulator: MongoDataManipulator;
    private lateinit var template: MongoTemplate;

    @BeforeEach
    fun setup() {
        val app = SpringWebApp.run(
            configs = listOf(
                MongoConfig::class.java
            ),
            args = arrayOf(
                "--spring.data.mongodb.uri=${mongo.replicaSetUrl}" // tell Spring where Mongo lives
            ),
            useRandomPort = true
        )
        manipulator = app.get(MongoDataManipulator::class.java)
        template = app.get(MongoTemplate::class.java)
    }

    @AfterEach
    fun clean() {
        template.dropCollection("kv_store") // test isolation
    }

    @Test
    fun `set and get JSON`() {
        val key = "player:42"
        val json = """{"name":"Ava","score":123}"""
        manipulator.setValue(key, SerializedValue.create(json, SerializationType.JSON))

        val found = manipulator.findValue(key)
        assertNotNull(found)
        assertEquals(SerializationType.JSON, found!!.getType())
        assertTrue(found.getValue().contains("\"name\""))
        assertTrue(found.getValue().contains("\"score\""))
    }

    @Test
    fun `delete on null`() {
        val key = "temp:1"
        manipulator.setValue(key, SerializedValue.create("""{"ok":true}""", SerializationType.JSON))
        assertNotNull(manipulator.findValue(key))

        manipulator.setValue(key, null) // delete
        assertNull(manipulator.findValue(key))
    }
}
package com.github.bratek20.spring.mongo

import com.github.bratek20.architecture.data.DataManipulatorTest
import com.github.bratek20.architecture.data.api.DataManipulator
import com.github.bratek20.logs.context.SystemLogsImpl
import com.github.bratek20.spring.webapp.SpringWebApp
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.data.mongodb.core.MongoTemplate
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class MongoDataManipulatorIntegrationTest: DataManipulatorTest() {
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
            modules = listOf(
                SystemLogsImpl(),
                DataMongoImpl()
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
        template.dropCollection(MongoDataManipulator.COLLECTION_NAME)
    }

    override fun create(): DataManipulator {
        return manipulator
    }
}
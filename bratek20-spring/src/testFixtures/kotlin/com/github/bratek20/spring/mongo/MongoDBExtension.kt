package com.github.bratek20.spring.mongo

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.MongoDBContainer

object GlobalMongo {
    @Volatile private var started = false
    lateinit var container: MongoDBContainer
        private set

    @Synchronized
    fun ensureStarted(image: String = "mongo:5.0.15") {
        if (!started) {
            container = MongoDBContainer(image).also { it.start() }
            Runtime.getRuntime().addShutdownHook(Thread { container.stop() })
            started = true
        }
    }
}

class MongoDBExtension : BeforeAllCallback {
    override fun beforeAll(context: ExtensionContext) {
        GlobalMongo.ensureStarted()
    }
    companion object {
        val replicaSetUrl get() = GlobalMongo.container.replicaSetUrl
    }
}
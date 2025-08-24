package com.github.bratek20.spring.mongo

import com.github.bratek20.architecture.data.api.DataManipulator
import com.github.bratek20.architecture.serialization.api.SerializationType
import com.github.bratek20.architecture.serialization.api.SerializedValue
import com.github.bratek20.logs.api.Logger
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service

@Service
class MongoDataManipulator(
    private val mongoTemplate: MongoTemplate,
    private val logger: Logger
) : DataManipulator {

    companion object {
        const val COLLECTION_NAME = "kv_store"
    }

    override fun findValue(keyName: String): SerializedValue? {
        val start = System.currentTimeMillis()
        require(keyName.isNotBlank()) { "keyName must not be blank" }

        val doc = mongoTemplate.findById(keyName, Map::class.java, COLLECTION_NAME)
        val jsonString = doc?.get("v") as? String ?: run {
            val end = System.currentTimeMillis()
            logger.debug("MongoDataManipulator.findValue('$keyName') took ${end - start}ms (miss)", this)
            return null
        }

        val end = System.currentTimeMillis()
        logger.debug("MongoDataManipulator.findValue('$keyName') took ${end - start}ms", this)
        return SerializedValue.create(jsonString, SerializationType.JSON)
    }

    override fun setValue(keyName: String, value: SerializedValue?) {
        val start = System.currentTimeMillis()
        require(keyName.isNotBlank()) { "keyName must not be blank" }

        if (value == null) {
            mongoTemplate.remove(Query(Criteria.where("_id").`is`(keyName)), COLLECTION_NAME)
            val end = System.currentTimeMillis()
            logger.debug("MongoDataManipulator.setValue('$keyName' -> null/delete) took ${end - start}ms", this)
            return
        }

        require(value.getType() == SerializationType.JSON) {
            "Only JSON is supported. Provided type: ${value.getType()}"
        }
        val jsonString = value.getValue()
        require(!jsonString.isNullOrBlank()) { "JSON string must not be blank" }

        val q = Query(Criteria.where("_id").`is`(keyName))
        val u = Update().set("v", jsonString)
        mongoTemplate.upsert(q, u, COLLECTION_NAME)

        val end = System.currentTimeMillis()
        logger.debug("MongoDataManipulator.setValue('$keyName') took ${end - start}ms", this)
    }
}

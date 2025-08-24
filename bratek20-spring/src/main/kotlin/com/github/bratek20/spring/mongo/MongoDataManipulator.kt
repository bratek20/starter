package com.github.bratek20.spring.mongo

import com.github.bratek20.architecture.data.api.DataManipulator
import com.github.bratek20.architecture.serialization.api.SerializationType
import com.github.bratek20.architecture.serialization.api.SerializedValue
import com.github.bratek20.logs.api.Logger
import org.bson.Document
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service

@Service
class MongoDataManipulator(
    private val mongoTemplate: MongoTemplate,
    private val logger: Logger // TODO-REF move times logging to DataStorage + move logs to architecture
) : DataManipulator {

    companion object {
        const val COLLECTION_NAME = "kv_store"
    }

    override fun findValue(keyName: String): SerializedValue? {
        val start = System.currentTimeMillis()
        require(keyName.isNotBlank()) { "keyName must not be blank" }

        // {_id: keyName, v: <Document>}
        val doc: Document? = mongoTemplate.findById(keyName, Document::class.java, COLLECTION_NAME)
        val payload: Document = doc?.get("v", Document::class.java) ?: return null

        // Convert back to JSON string for your SerializedValue
        val end = System.currentTimeMillis()
        logger.debug("MongoDataManipulator.findValue('$keyName') took ${end - start}ms", this)
        return SerializedValue.create(payload.toJson(), SerializationType.JSON)
    }

    override fun setValue(keyName: String, value: SerializedValue?) {
        val start = System.currentTimeMillis()
        require(keyName.isNotBlank()) { "keyName must not be blank" }

        // Null value means delete the key entirely
        if (value == null) {
            mongoTemplate.remove(Query(Criteria.where("_id").`is`(keyName)), COLLECTION_NAME)
            return
        }

        // We only support JSON here (as per your example)
        val jsonString = value.getValue()
        require(value.getType() == SerializationType.JSON) {
            "Only JSON is supported. Provided type: ${value.getType()}"
        }
        require(!jsonString.isNullOrBlank()) { "JSON string must not be blank" }

        // Validate/parse JSON → BSON Document
        val parsed = try {
            Document.parse(jsonString)
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid JSON for key '$keyName': ${e.message}", e)
        }

        // Upsert {_id: keyName, v: parsed}
        val q = Query(Criteria.where("_id").`is`(keyName))
        val u = Update().set("v", parsed)
        mongoTemplate.upsert(q, u, COLLECTION_NAME)
        val end = System.currentTimeMillis()
        logger.debug("MongoDataManipulator.setValue('$keyName') took ${end - start}ms", this)
    }
}
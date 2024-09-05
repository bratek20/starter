package com.github.bratek20.architecture.data.context

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.data.api.DataStorage
import com.github.bratek20.architecture.data.api.DataStorageIntegration
import com.github.bratek20.architecture.data.impl.DataStorageLogic
import com.github.bratek20.architecture.data.integrations.InMemoryDataStorageIntegration
import com.github.bratek20.architecture.properties.api.Properties
import com.github.bratek20.architecture.properties.impl.PropertiesLogic

class DataImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(DataStorage::class.java, DataStorageLogic::class.java)
    }
}

class DataInMemoryImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder
            .withModule(DataImpl())
            .setImpl(DataStorageIntegration::class.java, InMemoryDataStorageIntegration::class.java)
    }
}
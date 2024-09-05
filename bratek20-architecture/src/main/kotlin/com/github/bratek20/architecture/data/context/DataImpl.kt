package com.github.bratek20.architecture.data.context

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.data.api.DataStorage
import com.github.bratek20.architecture.data.api.DataManipulator
import com.github.bratek20.architecture.data.impl.DataStorageLogic
import com.github.bratek20.architecture.data.integrations.InMemoryDataManipulator

class DataImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(DataStorage::class.java, DataStorageLogic::class.java)
    }
}

class DataInMemoryImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder
            .withModule(DataImpl())
            .setImpl(DataManipulator::class.java, InMemoryDataManipulator::class.java)
    }
}
package com.github.bratek20.spring.mongo

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(
    MongoAutoConfiguration::class,
    MongoDataAutoConfiguration::class
)
@ComponentScan(basePackageClasses = [MongoDataManipulator::class])
class MongoConfig {
}

class DataMongoImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.addClass(MongoConfig::class.java)
    }
}
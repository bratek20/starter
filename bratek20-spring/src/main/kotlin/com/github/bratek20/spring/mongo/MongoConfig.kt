package com.github.bratek20.spring.mongo

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
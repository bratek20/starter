package com.github.bratek20.spring.mongo

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [MongoDataManipulator::class])
class MongoConfig {
}
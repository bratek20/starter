package com.github.bratek20.architecture.context.spring

import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.beans.factory.NoUniqueBeanDefinitionException
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import com.github.bratek20.architecture.context.api.ClassNotFoundInContextException
import com.github.bratek20.architecture.context.api.Context
import com.github.bratek20.architecture.context.api.MultipleClassesFoundInContextException

class SpringContext(val value: AnnotationConfigApplicationContext): Context {
    override fun <T: Any> get(type: Class<T>): T {
        try {
            return value.getBean(type)
        } catch (e: NoUniqueBeanDefinitionException) {
            val msg = e.message ?: "";

            val fullDependentClassName = msg.substringAfter("No qualifying bean of type '").substringBefore("'")
            val dependentClass = fullDependentClassName.substringAfterLast(".")

            throw MultipleClassesFoundInContextException("Multiple classes found for $dependentClass in context");
        } catch (e: NoSuchBeanDefinitionException) {
            val msg = e.message ?: "";
            val fullDependentClassName = msg.substringAfter("No qualifying bean of type '").substringBefore("'")
            val dependentClass = fullDependentClassName.substringAfterLast(".")

            throw ClassNotFoundInContextException("Class $dependentClass not found in context");
        }
    }

    override fun <T : Any> getMany(type: Class<T>): Set<T> {
        return value.getBeansOfType(type).values.toSet()
    }
}
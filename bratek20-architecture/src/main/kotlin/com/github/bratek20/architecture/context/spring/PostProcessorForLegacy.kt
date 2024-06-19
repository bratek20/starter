package com.github.bratek20.architecture.context.spring

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory

interface SpringContextBuilderProvider {
    fun provide(): SpringContextBuilder
}

class PostProcessorForLegacy(
    private val builderProvider: SpringContextBuilderProvider
): BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        val context = builderProvider.provide().build()
        context.value.beanDefinitionNames.forEach {
            val bean = context.value.getBean(it)
            if (!beanFactory.containsSingleton(it)) {
                beanFactory.registerSingleton(it, bean)
            }
        }
    }
}
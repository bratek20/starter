package pl.bratek20.architecture.context.spring

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory

interface SpringContextBuilderProvider {
    fun provide(): SpringContextBuilder
}

class PostProcessorForLegacy(
    private val builderProvider: SpringContextBuilderProvider
): BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        val builder = builderProvider.provide()
        builder.applyTo(beanFactory)
    }
}
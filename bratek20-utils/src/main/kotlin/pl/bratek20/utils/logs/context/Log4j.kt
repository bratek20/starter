package pl.bratek20.utils.logs.context

import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.api.ContextModule
import pl.bratek20.utils.logs.api.LoggerIntegration
import pl.bratek20.utils.logs.log4j.Log4jIntegration

class Log4jLoggerIntegrationImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(LoggerIntegration::class.java, Log4jIntegration::class.java)
    }
}
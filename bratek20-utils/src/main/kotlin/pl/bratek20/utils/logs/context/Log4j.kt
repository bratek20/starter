package pl.bratek20.utils.logs.context

import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.api.ContextModule
import pl.bratek20.utils.logs.api.Logger
import pl.bratek20.utils.logs.log4j.Log4jLogger

class Log4jLoggerImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(Logger::class.java, Log4jLogger::class.java)
    }
}
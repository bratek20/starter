package pl.bratek20.utils.logs.context

import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.api.ContextModule
import pl.bratek20.utils.logs.api.LoggerIntegration
import pl.bratek20.utils.logs.system.SystemIntegration

class SystemLoggerIntegrationImpl: ContextModule{
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(LoggerIntegration::class.java, SystemIntegration::class.java)
    }
}
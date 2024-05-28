package pl.bratek20.utils.logs

import nl.altindag.log.LogCaptor
import pl.bratek20.architecture.context.api.ContextModule
import pl.bratek20.utils.logs.context.Log4jLoggerIntegrationImpl
import pl.bratek20.utils.logs.log4j.Log4jIntegration

class Log4JLoggerIntegrationTest: LoggerTest() {
    private lateinit var logCaptor: LogCaptor

    override fun getIntegrationImplModule(): ContextModule {
        return Log4jLoggerIntegrationImpl()
    }

    override fun setUp() {
        logCaptor = LogCaptor.forClass(Log4jIntegration::class.java)
    }

    override fun getOutputLines(): List<String> {
        return logCaptor.logs
    }
}
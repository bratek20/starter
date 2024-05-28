package pl.bratek20.utils.logs

import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.Filter
import org.apache.logging.log4j.core.Layout
import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.LoggerContext
import org.apache.logging.log4j.core.appender.AbstractAppender
import org.apache.logging.log4j.core.layout.PatternLayout
import pl.bratek20.architecture.context.api.ContextModule
import pl.bratek20.utils.logs.context.Log4jLoggerImpl
import java.io.Serializable


class ListAppender protected constructor(name: String?, filter: Filter?, layout: Layout<out Serializable?>?) :
    AbstractAppender(name, filter, layout, false, null) {
    private val logMessages: MutableList<String> = ArrayList()

    override fun append(event: LogEvent) {
        logMessages.add(event.message.formattedMessage)
    }

    fun getLogMessages(): List<String> {
        return logMessages
    }

    companion object {
        fun createAppender(name: String?, filter: Filter?, layout: Layout<out Serializable?>?): ListAppender {
            return ListAppender(name, filter, layout)
        }
    }
}

class Log4jLoggerTest: LoggerTest() {
    private lateinit var listAppender: ListAppender

    override fun getImplModule(): ContextModule {
        return Log4jLoggerImpl()
    }

    override fun setUp() {
        val patternLayout = PatternLayout.newBuilder()
            .withPattern("%level: %msg%n")
            .build()

        listAppender = ListAppender.createAppender("ListAppender", null, patternLayout)
        listAppender.start()

        val context = LogManager.getContext(false) as LoggerContext
        val config = context.configuration
        val loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME)

        // Add appender to root logger config
        loggerConfig.addAppender(listAppender, Level.ALL, null)
        context.updateLoggers()
    }

    override fun getOutput(): String {
        return listAppender.getLogMessages().joinToString("\n")
    }
}
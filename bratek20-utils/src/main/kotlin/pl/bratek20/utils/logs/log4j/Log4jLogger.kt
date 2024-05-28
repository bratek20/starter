package pl.bratek20.utils.logs.log4j

import org.apache.logging.log4j.LogManager
import pl.bratek20.utils.logs.api.Logger

class Log4jLogger : Logger {
    private val logger = LogManager.getLogger(Log4jLogger::class.java)

    override fun info(message: String) {
        logger.info(message)
    }

    override fun warn(message: String) {
        logger.warn(message)
    }

    override fun error(message: String) {
        logger.error(message)
    }
}
package pl.bratek20.utils.logs.log4j

import org.apache.logging.log4j.LogManager
import pl.bratek20.utils.logs.api.LoggerIntegration

class Log4jIntegration : LoggerIntegration {
    private val logger = LogManager.getLogger(Log4jIntegration::class.java)

    override fun log(message: String) {
        logger.info(message)
    }
}
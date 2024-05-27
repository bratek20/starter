package pl.bratek20.utils.logs.impl

import pl.bratek20.utils.logs.api.Logger
import org.apache.logging.log4j.LogManager

class LoggerLogic : Logger {
    private val logger = LogManager.getLogger(LoggerLogic::class.java)

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
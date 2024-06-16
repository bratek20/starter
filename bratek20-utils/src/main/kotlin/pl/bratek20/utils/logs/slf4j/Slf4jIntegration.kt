package pl.bratek20.utils.logs.slf4j

import org.slf4j.LoggerFactory
import pl.bratek20.utils.logs.api.LoggerIntegration

class Slf4jIntegration : LoggerIntegration {
    private val logger = LoggerFactory.getLogger(Slf4jIntegration::class.java)

    override fun log(message: String) {
        logger.info(message)
    }
}
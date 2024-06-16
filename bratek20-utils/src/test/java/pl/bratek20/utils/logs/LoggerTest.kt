package pl.bratek20.utils.logs

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.bratek20.architecture.context.api.ContextModule
import pl.bratek20.architecture.context.someContextBuilder
import pl.bratek20.utils.logs.api.Logger
import pl.bratek20.utils.logs.context.LogsImpl

abstract class LoggerTest {

    protected abstract fun getIntegrationImplModule(): ContextModule
    protected abstract fun getOutputLines(): List<String>

    open fun setUp() {}
    open fun tearDown() {}

    private lateinit var logger: Logger

    @BeforeEach
    fun beforeEach() {
        setUp()
        logger = someContextBuilder()
            .withModule(LogsImpl())
            .withModule(getIntegrationImplModule())
            .get(Logger::class.java)
    }

    @AfterEach
    fun afterEach() {
        tearDown()
    }

    @Test
    fun shouldLog() {
        logger.info("message")
        logger.warn("message")
        logger.error("message")
        
        assertThat(getOutputLines()).containsExactly(
            "[INFO] message",
            "[WARN] message",
            "[ERROR] message"
        )
    }
}
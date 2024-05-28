package pl.bratek20.utils.logs

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.bratek20.architecture.context.api.ContextModule
import pl.bratek20.architecture.context.someContextBuilder
import pl.bratek20.utils.logs.api.Logger

abstract class LoggerTest {

    protected abstract fun getImplModule(): ContextModule
    protected abstract fun getOutput(): String

    open fun setUp() {}
    open fun tearDown() {}

    private lateinit var logger: Logger

    @BeforeEach
    fun beforeEach() {
        setUp()
        logger = someContextBuilder()
            .withModule(getImplModule())
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
        
        assertThat(getOutput())
            .isEqualTo(
                "INFO: message\n" +
                "WARN: message\n" +
                "ERROR: message\n"
            )
    }
}
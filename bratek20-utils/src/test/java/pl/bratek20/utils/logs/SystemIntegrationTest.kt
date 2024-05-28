package pl.bratek20.utils.logs

import pl.bratek20.architecture.context.api.ContextModule
import pl.bratek20.utils.logs.context.SystemLoggerIntegrationImpl
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class SystemIntegrationTest: LoggerTest() {
    override fun getIntegrationImplModule(): ContextModule {
        return SystemLoggerIntegrationImpl()
    }

    override fun getOutputLines(): List<String> {
        return outputStream.toString()
            .replace("\r\n", "\n")
            .split("\n")
            .filter { it.isNotBlank() }
    }

    private lateinit var outputStream: ByteArrayOutputStream
    private lateinit var originalOut: PrintStream


    override fun setUp() {
        originalOut = System.out
        outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
    }

    override fun tearDown() {
        System.setOut(originalOut)
    }
}
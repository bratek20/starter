package pl.bratek20.utils.logs

import pl.bratek20.architecture.context.api.ContextModule
import pl.bratek20.utils.logs.context.SystemLoggerImpl
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class SystemLoggerTest: LoggerTest() {
    override fun getImplModule(): ContextModule {
        return SystemLoggerImpl()
    }

    override fun getOutput(): String {
        return outputStream.toString().replace("\r\n", "\n")
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
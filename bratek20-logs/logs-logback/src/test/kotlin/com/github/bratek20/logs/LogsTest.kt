package com.github.bratek20.logs

import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.logs.api.Logger
import com.github.bratek20.logs.context.Slf4jLogsImpl
import com.github.bratek20.logs.logback.api.LogbackHelper
import com.github.bratek20.logs.logback.context.LogsLogbackImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Files
import java.nio.file.Path

class LogsTest {
    @TempDir
    private lateinit var tempDir: Path

    @Test
    fun test() {
        val c = someContextBuilder()
            .withModule(LogsLogbackImpl())
            .build()

        val logger = c.get(Logger::class.java)
        val helper = c.get(LogbackHelper::class.java)

        val logsPath = tempDir.resolve("logs.txt")
        helper.setOutputFile(logsPath.toString())

        logger.info("info")
        logger.warn("warn", this)

        val logsFileContent = Files.readString(logsPath)
        val logLines = logsFileContent.lines()
        assertThat(logLines).hasSize(3) // last line is empty

        assertThat(logLines[0]).contains("[INFO] info")
        assertThat(logLines[1]).contains("[WARN] warn")

        helper.disableOutputFile()
    }
}
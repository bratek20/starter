package pl.bratek20.utils.logs

import org.assertj.core.api.Assertions.assertThat
import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.api.ContextModule
import pl.bratek20.utils.logs.api.Logger

class LoggerMock: Logger {
    private val infos: MutableList<String> = mutableListOf()
    private val warns: MutableList<String> = mutableListOf()
    private val errors: MutableList<String> = mutableListOf()

    override fun info(message: String) {
        infos.add(message)
    }

    override fun warn(message: String) {
        warns.add(message)
    }

    override fun error(message: String) {
        errors.add(message)
    }

    fun assertInfos(vararg messages: String) {
        assertThat(infos).containsExactly(*messages)
    }

    fun assertWarns(vararg messages: String) {
        assertThat(warns).containsExactly(*messages)
    }

    fun assertErrors(vararg messages: String) {
        assertThat(errors).containsExactly(*messages)
    }
}

class LogsMocks: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(Logger::class.java, LoggerMock::class.java)
    }
}
package pl.bratek20.utils.logs.system

import pl.bratek20.utils.logs.api.LoggerIntegration

class SystemIntegration : LoggerIntegration {
    override fun log(message: String) {
        println(message)
    }
}
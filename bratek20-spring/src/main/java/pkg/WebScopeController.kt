package pkg

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.infrastructure.httpserver.api.WebServerModule
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApplicationController(private val applicationScopedBean: ApplicationScopedBean) {
    @GetMapping("/application-scope")
    fun getApplicationScopedBean(): String {
        return applicationScopedBean.id
    }
}
class ApplicationControllerModule : WebServerModule {
    override fun apply(builder: ContextBuilder) {
    }

    override fun getControllers(): List<Class<*>> {
        return listOf(ApplicationController::class.java)
    }
}

@RestController
class UserScopeController(private val userScopedBean: UserScopedBean) {
    @GetMapping("/user-scope")
    fun getUserScopedBean(): String {
        return userScopedBean.id
    }
}

class UserScopeControllerModule : WebServerModule {
    override fun apply(builder: ContextBuilder) {
    }

    override fun getControllers(): List<Class<*>> {
        return listOf(UserScopeController::class.java)
    }
}



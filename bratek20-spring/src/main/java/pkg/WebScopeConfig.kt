package pkg

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.annotation.SessionScope
import java.util.*
import javax.servlet.http.HttpSession

open class UserScopedBean(
    private val session: HttpSession
) {
    init {
        println("UserScopedBean created for session " + session.id)
    }

    open val id: String = UUID.randomUUID().toString()

    open val sessionUserId: String
        get() = session.getAttribute("userId").toString()
}

class ApplicationScopedBean {
    @JvmField
    val id: String = UUID.randomUUID().toString()
}

@Configuration
open class UserConfig {
    @Bean
    @SessionScope
    open fun userScopedBean(session: HttpSession): UserScopedBean {
        return UserScopedBean(session)
    }

    @Bean
    open fun userController(userScopedBean: UserScopedBean): UserScopeController {
        return UserScopeController(userScopedBean)
    }
}

class ApplicationModule : ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.addClass(ApplicationScopedBean::class.java)
    }
}

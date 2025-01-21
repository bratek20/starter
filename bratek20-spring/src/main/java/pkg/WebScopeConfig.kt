package pkg

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.annotation.SessionScope
import java.util.*
import javax.servlet.http.HttpSession

@Configuration
open class WebScopeConfig {
    @Bean
    @SessionScope
    open fun userScopedBean(session: HttpSession): UserScopedBean {
        return UserScopedBean(session)
    }

    @Bean
    open fun applicationScopedBean(): ApplicationScopedBean {
        return ApplicationScopedBean()
    }
}

@SessionScope
class UserScopedBean(private val session: HttpSession) {
    val id: String = UUID.randomUUID().toString()

    val sessionUserId: String
        get() = session.getAttribute("userId").toString()
}

class ApplicationScopedBean {
    @JvmField
    val id: String = UUID.randomUUID().toString()
}

class UserModule : ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.addClass(UserScopedBean::class.java)
    }
}

class ApplicationModule : ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.addClass(ApplicationScopedBean::class.java)
    }
}

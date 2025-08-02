package pkg;

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.infrastructure.httpserver.api.WebServerModule;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

@RestController
class AuthController {
    private var nextUserId: Int = 1
    private val authDatabase = ConcurrentHashMap<String, Int>()

    @GetMapping("/login")
    fun login(@RequestHeader("AuthId") authId: String, session: HttpSession): String {
        // If AuthId is not registered, create a new account or session
        val userId = authDatabase.computeIfAbsent(authId) { nextUserId++ }
        //session.setAttribute("userId", userId)
        return "Logged in as $userId"
    }
}

class AuthControllerImpl: WebServerModule {
    override fun getControllers(): List<Class<*>> {
        return listOf(AuthController::class.java)
    }

    override fun apply(builder: ContextBuilder) {
        // no-op
    }
}


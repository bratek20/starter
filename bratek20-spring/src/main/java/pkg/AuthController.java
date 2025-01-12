package pkg;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class AuthController {

    private int nextUserId = 1;
    private final ConcurrentHashMap<String, Integer> authDatabase = new ConcurrentHashMap<>();

    @GetMapping("/login")
    public String login(@RequestHeader("AuthId") String authId, HttpSession session) {
        // If AuthId is not registered, create a new account or session
        authDatabase.computeIfAbsent(authId, id -> nextUserId++);
        session.setAttribute("userId", authDatabase.get(authId));
        return "Logged in as " + authDatabase.get(authId);
    }

    @GetMapping("/secure-endpoint")
    public String secureEndpoint(HttpSession session) {
        if (session.getAttribute("userId") == null) {
            throw new RuntimeException("User not logged in");
        }
        return "Welcome back, " + session.getAttribute("userId");
    }
}


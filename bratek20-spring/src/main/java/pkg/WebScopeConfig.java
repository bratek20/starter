package pkg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpSession;

@Configuration
public class WebScopeConfig {

    @Bean
    @SessionScope
    public UserScopedBean userScopedBean(HttpSession session) {
        return new UserScopedBean(session);
    }

    @Bean
    public ApplicationScopedBean applicationScopedBean() {
        return new ApplicationScopedBean();
    }
}

class UserScopedBean {
    private final HttpSession session;

    UserScopedBean(HttpSession session) {
        this.session = session;
    }

    public String getId() {
        return session.getAttribute("userId").toString();
    }
}

class ApplicationScopedBean {
    private final String id = java.util.UUID.randomUUID().toString();

    public String getId() {
        return id;
    }
}

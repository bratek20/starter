package pkg;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebScopeController {

    private final UserScopedBean userScopedBean;
    private final ApplicationScopedBean applicationScopedBean;

    public WebScopeController(UserScopedBean userScopedBean, ApplicationScopedBean applicationScopedBean) {
        this.userScopedBean = userScopedBean;
        this.applicationScopedBean = applicationScopedBean;
    }

    @GetMapping("/user-scope")
    public String getUserScopedBean() {
        return userScopedBean.getId();
    }

    @GetMapping("/application-scope")
    public String getApplicationScopedBean() {
        return applicationScopedBean.getId();
    }
}

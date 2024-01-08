package pl.bratek20.commons.user.impl.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.bratek20.commons.identity.api.IdentityApi;
import pl.bratek20.commons.identity.impl.IdentityConfig;
import pl.bratek20.commons.user.api.UserApi;
import pl.bratek20.commons.user.impl.application.UserRepository;
import pl.bratek20.commons.user.impl.application.UserService;

@Configuration
@Import({
    IdentityConfig.class
})
public class UserConfig {
    @Bean
    public UserApi userApi(UserRepository userRepository, IdentityApi identityApi) {
        return new UserService(userRepository, identityApi);
    }
}

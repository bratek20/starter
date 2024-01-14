package pl.bratek20.commons;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.bratek20.commons.user.impl.infrastructure.persistance.CrudRepositoryConfig;
import pl.bratek20.commons.user.web.UserWebServerConfig;
import pl.bratek20.spring.persistence.PersistenceInMemoryConfig;
import pl.bratek20.spring.web.WebApp;

public class Main {
    @Configuration
    @EnableAutoConfiguration
    @Import({
        PersistenceInMemoryConfig.class,
        CrudRepositoryConfig.class,
        UserWebServerConfig.class
    })
    static class MainConfig {

    }
    public static void main(String[] args) {
        WebApp.run(MainConfig.class, new String[]{"--debug"});
    }
}

package pl.bratek20.spring.data;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.MySQLContainer;

public class MySQLExtension implements BeforeAllCallback {
    public static final String ROOT_USERNAME = "root";
    public static final String ROOT_PASSWORD = "password";
    public static final String DATABASE_NAME = "test_db";

    @Override
    public void beforeAll(ExtensionContext context) {
        MySQLContainer<?> container = new MySQLContainer<>("mysql:8.0")
            .withUsername(ROOT_USERNAME)
            .withPassword(ROOT_PASSWORD)
            .withDatabaseName(DATABASE_NAME)
            .withInitScript("init_db.sql");

        container.start();
        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", "application-user");
        System.setProperty("spring.datasource.password", "application-user-password");

    }
}

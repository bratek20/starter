package pl.bratek20.spring.flyway.impl;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import pl.bratek20.spring.flyway.api.FlywayMigration;

import java.util.Collection;

@RequiredArgsConstructor
public class FlywayMigrator {
    private static final String FLYWAY_USERNAME = "flyway-user";
    private static final String FLYWAY_PASSWORD = "flyway-user-password";

    private final String dataSourceUrl;
    private final Collection<FlywayMigration> migrations;

    public void migrate() {
        migrations.forEach(this::migrateModule);
    }

    private void migrateModule(FlywayMigration migration) {
        Flyway flyway = createFlywayInstance(migration);
        flyway.migrate();
    }

    private Flyway createFlywayInstance(FlywayMigration migration) {
        String migrationFolderPath = migration.moduleName() + "/db/migration";

        return Flyway.configure()
                   .locations(migrationFolderPath)
                   .dataSource(dataSourceUrl, FLYWAY_USERNAME, FLYWAY_PASSWORD)
                   .schemas(migration.moduleName())
                   .createSchemas(true)
                   .load();
    }
}

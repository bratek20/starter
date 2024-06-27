package pl.bratek20.spring.data.dbcleaner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
@Slf4j
public class DBCleaner {
    private final JdbcTemplate jdbcTemplate;

    public void deleteAll(String schema, String tableName) {
        if (tableName.equals("flyway_schema_history")) {
            log.info("Skipping deleting flyway_schema_history");
            return;
        }

        String fullTableName = String.format("%s.%s", schema, tableName);
        log.info("Deleting all from {}", fullTableName);
        jdbcTemplate.update(String.format("DELETE FROM %s", fullTableName));
    }

    public void deleteAllTables(String schema) {
        jdbcTemplate.queryForList(String.format("SHOW TABLES from %s;", schema)).forEach(table -> {
            String tableName = table.values().iterator().next().toString();
            deleteAll(schema, tableName);
        });
    }
}

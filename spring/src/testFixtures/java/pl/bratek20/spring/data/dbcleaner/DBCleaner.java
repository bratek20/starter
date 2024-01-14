package pl.bratek20.spring.data.dbcleaner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
@Slf4j
public class DBCleaner {
    private final JdbcTemplate jdbcTemplate;

    public void deleteAll(String tableName) {
        if (tableName.equals("flyway_schema_history")) {
            log.info("Skipping deleting flyway_schema_history");
            return;
        }
        log.info("Deleting all from {}", tableName);
        jdbcTemplate.update(String.format("DELETE FROM %s", tableName));
    }

    public void deleteAllTables() {
        jdbcTemplate.queryForList("SHOW TABLES").forEach(table -> {
            String tableName = table.values().iterator().next().toString();
            deleteAll(tableName);
        });
    }
}

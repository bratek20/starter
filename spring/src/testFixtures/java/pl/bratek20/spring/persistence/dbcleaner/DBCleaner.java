package pl.bratek20.spring.persistence.dbcleaner;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class DBCleaner {
    private final JdbcTemplate jdbcTemplate;

    public void deleteAll(String tableName) {
        jdbcTemplate.update(String.format("DELETE FROM %s", tableName));
    }

    public void deleteAllTables() {
        jdbcTemplate.queryForList("SHOW TABLES").forEach(table -> {
            String tableName = table.values().iterator().next().toString();
            deleteAll(tableName);
        });
    }
}

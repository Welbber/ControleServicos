package br.com.vital.controle_servico.config;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

public class PostgreSQLCleaner implements AfterEachCallback {
    private static final String TRUNCATE = "TRUNCATE $1 CASCADE";

    public PostgreSQLCleaner() {
    }

    private static void tryTruncate(JdbcTemplate jdbcTemplate, String table, int attempt) {
        try {
            jdbcTemplate.execute("TRUNCATE $1 CASCADE".replace("$1", table));
        } catch (DataAccessException var4) {
            if (attempt >= 3) {
                throw var4;
            }

            tryTruncate(jdbcTemplate, table, attempt + 1);
        }

    }

    private String getSelect(ExtensionContext extensionContext) {
        Environment environment = (Environment) SpringExtension.getApplicationContext(extensionContext).getBean(Environment.class);
        String where = (String) this.getProperty(environment).stream().collect(Collectors.joining("','", "'", "'"));
        return "SELECT table_name\nFROM information_schema.tables\nWHERE table_schema = 'public'\nAND table_name NOT IN (" + where + ")\nORDER BY table_name\n";
    }

    private List<String> getProperty(Environment environment) {
        return (List) environment.getProperty("tables.test.tablesIgnoredOnTruncate", List.class, List.of("CONFIGURATION_WITHOUT_TABLE"));
    }

    public void afterEach(ExtensionContext extensionContext) {
        this.cleaningDatabase(extensionContext);
    }

    private void cleaningDatabase(ExtensionContext extensionContext) {
        JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringExtension.getApplicationContext(extensionContext).getBean(JdbcTemplate.class);
        List<String> tables = jdbcTemplate.queryForList(this.getSelect(extensionContext), String.class);
        tables.forEach((table) -> {
            tryTruncate(jdbcTemplate, table, 1);
        });
    }
}

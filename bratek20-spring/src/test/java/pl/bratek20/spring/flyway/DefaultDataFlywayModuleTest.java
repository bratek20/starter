package pl.bratek20.spring.flyway;

import org.junit.jupiter.api.extension.ExtendWith;
import pl.bratek20.spring.data.DefaultDataConfig;
import pl.bratek20.spring.data.MySQLExtension;

@ExtendWith(MySQLExtension.class)
public class DefaultDataFlywayModuleTest extends BaseFlywayModuleTest {

    @Override
    protected Class<?> getDataConfig() {
        return DefaultDataConfig.class;
    }
}

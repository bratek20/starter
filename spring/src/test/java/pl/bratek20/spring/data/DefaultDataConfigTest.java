package pl.bratek20.spring.data;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MySQLExtension.class)
class DefaultDataConfigTest extends DataConfigBaseTest {

    @Override
    Class<?> getConfigClass() {
        return DefaultDataConfig.class;
    }
}
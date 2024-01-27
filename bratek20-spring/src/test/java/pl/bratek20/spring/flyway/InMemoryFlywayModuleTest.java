package pl.bratek20.spring.flyway;

import pl.bratek20.spring.data.InMemoryDataConfig;

public class InMemoryFlywayModuleTest extends BaseFlywayModuleTest {

    @Override
    protected Class<?> getDataConfig() {
        return InMemoryDataConfig.class;
    }
}

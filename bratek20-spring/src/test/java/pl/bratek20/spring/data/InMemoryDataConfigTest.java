package pl.bratek20.spring.data;

class InMemoryDataConfigTest extends DataConfigBaseTest {

    @Override
    Class<?> getConfigClass() {
        return InMemoryDataConfig.class;
    }
}
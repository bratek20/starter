package pl.bratek20.spring.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bratek20.spring.context.SpringContext;
import pl.bratek20.spring.context.SpringContextBuilder;
import pl.bratek20.spring.flyway.impl.FlywayConfig;
import pl.bratek20.testpackage.somemodule.SomeModuleConfig;
import pl.bratek20.testpackage.somemodule.SomeTableEntity;
import pl.bratek20.testpackage.somemodule.SomeModuleRepository;

import static org.assertj.core.api.Assertions.assertThat;

abstract class DataConfigBaseTest {
    abstract Class<?> getConfigClass();

    private SpringContext context;

    @BeforeEach
    void beforeEach() {
        context = new SpringContextBuilder(
            getConfigClass(),
            FlywayConfig.class,
            SomeModuleConfig.class
        ).build();
    }

    @Test
    void shouldGetSomeModuleRepository() {
        var repo = context.get(SomeModuleRepository.class);

        assertThat(repo).isNotNull();
    }

    @Test
    void shouldSupportCrudOperations() {
        var repo = context.get(SomeModuleRepository.class);

        var entity = new SomeTableEntity();
        entity.id = 1L;
        repo.save(entity);

        var savedEntity = repo.findById(1L);
        assertThat(savedEntity).isPresent();

        repo.delete(entity);

        var deletedEntity = repo.findById(1L);
        assertThat(deletedEntity).isEmpty();
    }
}

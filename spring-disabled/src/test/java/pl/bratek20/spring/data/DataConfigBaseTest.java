package pl.bratek20.spring.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.repository.CrudRepository;
import pl.bratek20.spring.context.SpringContext;
import pl.bratek20.spring.context.SpringContextBuilder;
import pl.bratek20.spring.flyway.impl.FlywayConfig;
import pl.bratek20.testpackage.somemodule.*;

import java.util.function.BiConsumer;
import java.util.function.Function;

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
    void shouldSupportCrudOperations_AutoIncrement() {
        var repo = context.get(AutoIncrementRepository.class);

        var newEntity = new AutoIncrementEntity("value");

        testCrudOperations(
            repo,
            newEntity,
            AutoIncrementEntity::setSomeValue,
            AutoIncrementEntity::getSomeValue
        );
    }

    @Test
    void shouldSupportCrudOperations_OwnId() {
        var repo = context.get(OwnIdRepository.class);

        var newEntity = new OwnIdEntity(1L);
        newEntity.setSomeValue("value");

        testCrudOperations(
            repo,
            newEntity,
            OwnIdEntity::setSomeValue,
            OwnIdEntity::getSomeValue
        );
    }

    <T> void testCrudOperations(
        CrudRepository<T, Long> repo,
        T newEntity,
        BiConsumer<T, String> setValue,
        Function<T, String> getValue
    ) {
        repo.save(newEntity);

        var savedEntity = repo.findById(1L);
        assertThat(savedEntity).isPresent();
        assertThat(getValue.apply(savedEntity.get())).isEqualTo("value");

        setValue.accept(newEntity, "value2");
        repo.save(newEntity);

        var updatedEntity = repo.findById(1L);
        assertThat(updatedEntity).isPresent();
        assertThat(getValue.apply(updatedEntity.get())).isEqualTo("value2");

        repo.delete(newEntity);

        var deletedEntity = repo.findById(1L);
        assertThat(deletedEntity).isEmpty();
    }
}

package pl.bratek20.spring.data;

import org.junit.jupiter.api.Test;
import pl.bratek20.somepackage.somemodule.SomeModuleConfig;
import pl.bratek20.somepackage.somemodule.SomeModuleEntity;
import pl.bratek20.somepackage.somemodule.SomeModuleRepository;
import pl.bratek20.spring.context.SpringContextBuilder;

import static org.assertj.core.api.Assertions.assertThat;

abstract class DataConfigBaseTest {
    abstract Class<?> getConfigClass();

    private SomeModuleRepository getRepository() {
        var context = new SpringContextBuilder(
            getConfigClass(),
            SomeModuleConfig.class
        ).build();

        return context.get(SomeModuleRepository.class);
    }

    @Test
    void shouldGetRepository() {
        var repo = getRepository();

        assertThat(repo).isNotNull();
    }

    @Test
    void shouldSaveEntity() {
        var repo = getRepository();

        var entity = new SomeModuleEntity();
        entity.id = 1L;
        repo.save(entity);
    }
}

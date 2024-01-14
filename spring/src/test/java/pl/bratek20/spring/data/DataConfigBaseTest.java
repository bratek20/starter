package pl.bratek20.spring.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bratek20.spring.context.SpringContext;
import pl.bratek20.testpackage.othermodule.OtherModuleConfig;
import pl.bratek20.testpackage.othermodule.OtherModuleEntity;
import pl.bratek20.testpackage.othermodule.OtherModuleRepository;
import pl.bratek20.testpackage.somemodule.SomeModuleConfig;
import pl.bratek20.testpackage.somemodule.SomeModuleEntity;
import pl.bratek20.testpackage.somemodule.SomeModuleRepository;
import pl.bratek20.spring.context.SpringContextBuilder;

import static org.assertj.core.api.Assertions.assertThat;

abstract class DataConfigBaseTest {
    abstract Class<?> getConfigClass();

    private SpringContext context;

    @BeforeEach
    void beforeEach() {
        context = new SpringContextBuilder(
            getConfigClass(),
            SomeModuleConfig.class,
            OtherModuleConfig.class
        ).build();
    }

    @Test
    void shouldGetSomeModuleRepository() {
        var repo = context.get(SomeModuleRepository.class);

        assertThat(repo).isNotNull();
    }

    @Test
    void shouldSaveSomeModuleEntity() {
        var repo = context.get(SomeModuleRepository.class);

        var entity = new SomeModuleEntity();
        entity.id = 1L;
        repo.save(entity);
    }

    @Test
    void shouldSaveOtherModuleEntity() {
        var repo = context.get(OtherModuleRepository.class);

        var entity = new OtherModuleEntity();
        entity.id = 1L;
        repo.save(entity);
    }
}

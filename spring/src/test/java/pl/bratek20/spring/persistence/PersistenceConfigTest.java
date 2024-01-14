package pl.bratek20.spring.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.bratek20.somepackage.somemodule.SomeModuleConfig;
import pl.bratek20.somepackage.somemodule.SomeModuleEntity;
import pl.bratek20.somepackage.somemodule.SomeModuleRepository;
import pl.bratek20.spring.context.SpringContextBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MySQLExtension.class)
class PersistenceConfigTest {

    @Test
    void shouldGetRepository() {
        var context = new SpringContextBuilder(
            PersistenceConfig.class,
            SomeModuleConfig.class
        ).build();

        var repo = context.get(SomeModuleRepository.class);
        assertThat(repo).isNotNull();
    }

    @Test
    void shouldSaveEntity() {
        var context = new SpringContextBuilder(
            PersistenceConfig.class,
            SomeModuleConfig.class
        ).build();

        var repo = context.get(SomeModuleRepository.class);

        var entity = new SomeModuleEntity();
        entity.id = 1L;
        repo.save(entity);
    }


}
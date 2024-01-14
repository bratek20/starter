package pl.bratek20.spring.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.repository.CrudRepository;
import pl.bratek20.spring.context.SpringContextBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MySQLExtension.class)
class PersistenceConfigTest {

    @Test
    void shouldGetRepository() {
        var context = new SpringContextBuilder(
            PersistenceConfig.class
        ).build();

        var repo = context.get(TestRepository.class);
        assertThat(repo).isNotNull();
    }

    @Test
    void shouldSaveEntity() {
        var context = new SpringContextBuilder(
            PersistenceConfig.class
        ).build();

        var repo = context.get(TestRepository.class);

        var entity = new TestEntity();
        entity.id = 1L;
        repo.save(entity);
    }


}
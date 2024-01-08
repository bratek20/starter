package pl.bratek20.commons.user.impl.infrastructure.persistance;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CrudUserEntityRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByName(String name);
}


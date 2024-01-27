package pl.bratek20.testpackage.somemodule;

import org.springframework.data.repository.CrudRepository;

public interface OwnIdRepository extends CrudRepository<OwnIdEntity, Long> {}


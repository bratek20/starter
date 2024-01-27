package pl.bratek20.testpackage.somemodule;

import org.springframework.data.repository.CrudRepository;

public interface AutoIncrementRepository extends CrudRepository<AutoIncrementEntity, Long> {}


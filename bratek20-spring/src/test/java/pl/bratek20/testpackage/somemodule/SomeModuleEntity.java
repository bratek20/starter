package pl.bratek20.testpackage.somemodule;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "some_module_table")
public class SomeModuleEntity {

    @Id
    public Long id;
}

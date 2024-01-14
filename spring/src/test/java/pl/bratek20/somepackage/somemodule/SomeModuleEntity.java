package pl.bratek20.somepackage.somemodule;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "test")
public class SomeModuleEntity {

    @Id
    public Long id;
}

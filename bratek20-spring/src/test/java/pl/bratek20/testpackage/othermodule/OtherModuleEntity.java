package pl.bratek20.testpackage.othermodule;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "other_module_table")
public class OtherModuleEntity {

    @Id
    public Long id;
}

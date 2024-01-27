package pl.bratek20.testpackage.othermodule;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "other_module_table")
public class OtherModuleEntity {

    @Id
    public Long id;
}

package pl.bratek20.testpackage.somemodule;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "some_module_table")
public class SomeModuleEntity {

    @Id
    public Long id;
}

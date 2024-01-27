package pl.bratek20.testpackage.somemodule;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "some_module.some_table")
public class SomeTableEntity {

    @Id
    public Long id;
}

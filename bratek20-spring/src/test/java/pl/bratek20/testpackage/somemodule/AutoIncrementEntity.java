package pl.bratek20.testpackage.somemodule;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "some_module", name = "auto_increment_table")
@Getter
public class AutoIncrementEntity {
    @Id
    private Long id;

    @Setter
    private String someValue;

    public AutoIncrementEntity(String someValue) {
        this.someValue = someValue;
    }
}

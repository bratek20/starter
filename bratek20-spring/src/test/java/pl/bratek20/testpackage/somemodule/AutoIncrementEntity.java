package pl.bratek20.testpackage.somemodule;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "some_module", name = "auto_increment_table")
@Getter
public class AutoIncrementEntity {
    @Id
    private Long id;
    @Setter
    private String value;

    public AutoIncrementEntity(String value) {
        this.value = value;
    }
}

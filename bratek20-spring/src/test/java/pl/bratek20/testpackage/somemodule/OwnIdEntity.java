package pl.bratek20.testpackage.somemodule;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "some_module", name = "own_id_table")
@RequiredArgsConstructor
public class OwnIdEntity {
    @Id
    @Getter
    private final Long id;

    @Getter
    @Setter
    private String value;

    @Version
    private Integer version;
}

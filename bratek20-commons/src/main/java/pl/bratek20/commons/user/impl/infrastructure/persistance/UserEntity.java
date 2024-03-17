package pl.bratek20.commons.user.impl.infrastructure.persistance;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@Table(schema = "users", name = "users")
class UserEntity {

    @Id
    private Long id;

    private Long identityId;
    private String name;
    private String password;
}


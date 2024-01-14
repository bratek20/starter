package pl.bratek20.spring.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "test")
class TestEntity {

    @Id
    Long id;
}

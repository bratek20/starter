create TABLE some_module.auto_increment_table
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    some_value VARCHAR(255) NOT NULL
);

create TABLE some_module.own_id_table
(
    id BIGINT PRIMARY KEY,
    some_value VARCHAR(255) NOT NULL,
    version INT
);
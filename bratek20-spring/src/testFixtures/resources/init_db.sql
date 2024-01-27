CREATE USER 'flyway-user' IDENTIFIED BY 'flyway-user-password';
CREATE USER 'application-user' IDENTIFIED BY 'application-user-password';

GRANT ALL PRIVILEGES ON *.* TO 'flyway-user';
GRANT ALL PRIVILEGES ON *.* TO 'application-user';
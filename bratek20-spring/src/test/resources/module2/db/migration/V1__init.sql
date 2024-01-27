# DO $$
# DECLARE
# moduleName text := 'module2';
# BEGIN
# -- Grant usage on the schema
# EXECUTE 'GRANT USAGE ON SCHEMA ' || moduleName || ' TO "application-user";';
#
# -- Alter default privileges for the user
# EXECUTE 'ALTER DEFAULT PRIVILEGES FOR USER "flyway" IN SCHEMA ' || moduleName || ' GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO "application-user";';
# END $$;
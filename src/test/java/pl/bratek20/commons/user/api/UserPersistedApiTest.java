package pl.bratek20.commons.user.api;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class UserPersistedApiTest extends UserApiTest {
    @Test
    void shouldPersistCreatedUser() {
        User user = new User("name", "password");
        var id = api.create(user);

        var newApi = createApi();
        var idFromNewApi = newApi.getIdentityId(user);

        assertThat(idFromNewApi).isEqualTo(id);
    }
}

package pl.bratek20.commons.user.api;

import org.junit.jupiter.api.Test;
import com.github.bratek20.architecture.tests.ApiTest;
import pl.bratek20.commons.user.api.exceptions.UserAlreadyExistsException;
import pl.bratek20.commons.user.api.exceptions.UserNotExistsException;
import pl.bratek20.commons.user.api.exceptions.WrongUserPasswordException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public abstract class UserApiTest extends ApiTest<UserApi> {

    @Test
    void shouldCreateUser() {
        User user = new User("name", "password");

        var id = api.create(user);

        assertThat(id).isNotNull();
    }

    @Test
    void shouldCreateDifferentUsers() {
        User user1 = new User("name1", "password");
        User user2 = new User("name2", "password");

        var id1 = api.create(user1);
        var id2 = api.create(user2);

        assertThat(id1).isNotEqualTo(id2);
    }

    @Test
    void shouldGetIdentityId() {
        User user = new User("name", "password");
        var id = api.create(user);

        var identityId = api.getIdentityId(user);

        assertThat(identityId).isEqualTo(id);
    }

    @Test
    void shouldThrowOnCreateWhenUserAlreadyExists() {
        User user = new User("name", "password");
        User sameLoginUser = new User("name", "otherPassword");
        api.create(user);

        assertThatThrownBy(() -> api.create(sameLoginUser))
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    @Test
    void shouldThrowOnGetIdentityIdWhenUserNotExists() {
        User user = new User("name", "password");

        assertThatThrownBy(() -> api.getIdentityId(user))
                .isInstanceOf(UserNotExistsException.class);
    }

    @Test
    void shouldThrowOnGetIdentityIdWhenPasswordIsWrong() {
        User user = new User("name", "password");
        User wrongPasswordUser = new User("name", "wrongPassword");
        api.create(user);

        assertThatThrownBy(() -> api.getIdentityId(wrongPasswordUser))
                .isInstanceOf(WrongUserPasswordException.class);
    }
}
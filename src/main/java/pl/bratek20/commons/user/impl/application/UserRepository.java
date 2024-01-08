package pl.bratek20.commons.user.impl.application;

import java.util.Optional;

public interface UserRepository {

    boolean hasUserLogin(String login);

    void save(UserIdentity user);

    Optional<UserIdentity> findByLogin(String login);
}

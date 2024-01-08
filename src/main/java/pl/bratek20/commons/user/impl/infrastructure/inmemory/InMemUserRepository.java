package pl.bratek20.commons.user.impl.infrastructure.inmemory;

import pl.bratek20.commons.user.impl.application.UserIdentity;
import pl.bratek20.commons.user.impl.application.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemUserRepository implements UserRepository {
    private Map<String, UserIdentity> users = new HashMap<>();

    @Override
    public boolean hasUserLogin(String login) {
        return users.containsKey(login);
    }

    @Override
    public void save(UserIdentity user) {
        users.put(user.user().name(), user);
    }

    @Override
    public Optional<UserIdentity> findByLogin(String login) {
        return Optional.ofNullable(users.get(login));
    }
}

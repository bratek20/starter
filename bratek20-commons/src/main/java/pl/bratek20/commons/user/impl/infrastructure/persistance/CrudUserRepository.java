package pl.bratek20.commons.user.impl.infrastructure.persistance;

import lombok.RequiredArgsConstructor;
import pl.bratek20.commons.identity.api.IdentityId;
import pl.bratek20.commons.user.api.User;
import pl.bratek20.commons.user.impl.application.UserIdentity;
import pl.bratek20.commons.user.impl.application.UserRepository;
import java.util.Optional;

@RequiredArgsConstructor
class CrudUserRepository implements UserRepository {

    private final CrudUserEntityRepository repository;

    @Override
    public boolean hasUserLogin(String login) {
        return repository.findByName(login).isPresent();
    }

    @Override
    public void save(UserIdentity user) {
        var e = new UserEntity();
        e.setIdentityId(user.identityId().value());
        e.setName(user.user().name());
        e.setPassword(user.user().password());
        repository.save(e);
    }

    @Override
    public Optional<UserIdentity> findByLogin(String login) {
        return repository.findByName(login)
            .map(u -> new UserIdentity(
                new User(u.getName(), u.getPassword()),
                new IdentityId(u.getIdentityId())
            ));
    }
}


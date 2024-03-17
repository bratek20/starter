package pl.bratek20.commons.user.impl.application;

import pl.bratek20.commons.identity.api.IdentityId;
import pl.bratek20.commons.user.api.User;

public record UserIdentity(User user, IdentityId identityId) {

}

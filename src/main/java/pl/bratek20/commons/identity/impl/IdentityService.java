package pl.bratek20.commons.identity.impl;

import pl.bratek20.commons.identity.api.IdentityApi;
import pl.bratek20.commons.identity.api.IdentityId;

public class IdentityService implements IdentityApi {
    private int nextId = 1;

    @Override
    public IdentityId generate() {
        return new IdentityId(nextId++);
    }
}

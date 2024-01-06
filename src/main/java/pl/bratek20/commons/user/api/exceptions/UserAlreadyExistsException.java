package pl.bratek20.commons.user.api.exceptions;

import pl.bratek20.commons.modules.api.ApiException;

public class UserAlreadyExistsException extends ApiException {

    public UserAlreadyExistsException(String login) {
        super("User with name " + login + " already exists");
    }
}

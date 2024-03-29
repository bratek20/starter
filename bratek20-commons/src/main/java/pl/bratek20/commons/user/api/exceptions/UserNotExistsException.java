package pl.bratek20.commons.user.api.exceptions;

import pl.bratek20.commons.modules.api.ApiException;

public class UserNotExistsException extends ApiException {

    public UserNotExistsException(String login) {
        super("User with name " + login + " not exists");
    }
}

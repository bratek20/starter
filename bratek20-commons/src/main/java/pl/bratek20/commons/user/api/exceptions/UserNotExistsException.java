package pl.bratek20.commons.user.api.exceptions;

import com.github.bratek20.architecture.exceptions.ApiException;

public class UserNotExistsException extends ApiException {

    public UserNotExistsException(String login) {
        super("User with name " + login + " not exists");
    }
}

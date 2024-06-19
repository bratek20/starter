package pl.bratek20.commons.user.api.exceptions;

import com.github.bratek20.architecture.exceptions.ApiException;

public class UserAlreadyExistsException extends ApiException {

    public UserAlreadyExistsException(String login) {
        super("User with name " + login + " already exists");
    }
}

package pl.bratek20.commons.user.api.exceptions;

import pl.bratek20.architecture.exceptions.ApiException;

public class WrongUserPasswordException extends ApiException {
    public WrongUserPasswordException() {
        super("Wrong user password");
    }
}

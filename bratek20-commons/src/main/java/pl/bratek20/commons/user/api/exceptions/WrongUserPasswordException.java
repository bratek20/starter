package pl.bratek20.commons.user.api.exceptions;

import pl.bratek20.commons.modules.api.ApiException;

public class WrongUserPasswordException extends ApiException {
    public WrongUserPasswordException() {
        super("Wrong user password");
    }
}

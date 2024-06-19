package pl.bratek20.commons.user.api.exceptions;

import com.github.bratek20.architecture.exceptions.ApiException;

public class WrongUserPasswordException extends ApiException {
    public WrongUserPasswordException() {
        super("Wrong user password");
    }
}

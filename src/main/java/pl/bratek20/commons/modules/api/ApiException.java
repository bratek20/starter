package pl.bratek20.commons.modules.api;

public class ApiException extends RuntimeException {

    public ApiException() {
        super();
    }
    public ApiException(String message) {
        super(message);
    }
}

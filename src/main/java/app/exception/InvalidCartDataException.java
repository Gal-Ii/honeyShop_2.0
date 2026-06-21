package app.exception;

public class InvalidCartDataException extends RuntimeException {
    public InvalidCartDataException(String message) {
        super(message);
    }
}

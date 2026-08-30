package src.exceptions;

public class IllegalValueException extends RuntimeException {
    public IllegalValueException(String message) {
        super(message);
    }
}
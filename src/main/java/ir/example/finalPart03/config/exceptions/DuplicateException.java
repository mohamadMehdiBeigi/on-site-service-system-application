package ir.example.finalPart03.config.exceptions;

public class DuplicateException extends RuntimeException{

    public DuplicateException(String message) {
        super(message);
    }

    public DuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}

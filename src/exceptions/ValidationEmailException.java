package exceptions;

public class ValidationEmailException extends Exception{
    public ValidationEmailException() {
    }

    public ValidationEmailException(String message) {
        super(message);
    }
}

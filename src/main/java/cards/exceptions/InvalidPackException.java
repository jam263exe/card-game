package cards.exceptions;

public class InvalidPackException extends RuntimeException {
    public InvalidPackException() {
        super("The card pack supplied is not valid.");
    }
}

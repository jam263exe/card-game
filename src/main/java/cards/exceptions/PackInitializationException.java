package cards.exceptions;

public class PackInitializationException extends RuntimeException {
    public PackInitializationException() {
        super("Error initializing pack. Expected / actual loaded card mismatch.");
    }
}

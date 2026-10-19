package cards.exceptions;

public class PackInitializationError extends RuntimeException {
    public PackInitializationError() {
        super("Error initializing pack. Expected / actual loaded card mismatch.");
    }
}

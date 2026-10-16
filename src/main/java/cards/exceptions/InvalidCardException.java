package cards.exceptions;

public class InvalidCardException extends RuntimeException {
    public InvalidCardException() {
        super("Card not found.");
    }

    public InvalidCardException(int cardId) {
        super("Card with cardId " + Integer.toString(cardId) + "not found.");
    }
}

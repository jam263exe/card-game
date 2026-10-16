package cards;

/**
 * Class for a single card object.
 */
public class Card {
    // value is encapsulated and immutable
    private final int value;
    private final int cardId;
    /**
     * Creates new card with an associated immutable value
     * @param value the only value associated with the card.
     *              A single integer.
     */
    public Card(int value, int cardId) {
        this.cardId = cardId;
        this.value = value;
    }

    /**
     * Returns the value of the card
     * @return value
     */
    public int getValue() {
        return this.value;
    }

    public int getCardId() {
        return this.cardId;
    }
}
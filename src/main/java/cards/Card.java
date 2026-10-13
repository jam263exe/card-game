package cards;

/**
 * Class for a single card object.
 */
public class Card {
    // value is encapsulated and immutable
    private final int value;

    /**
     * Creates new card with an associated immutable value
     * @param value the only value associated with the card.
     *              A single integer.
     */
    public Card(int value) {
        this.value = value;
    }

    /**
     * Returns the value of the card
     * @return value
     */
    public int getValue() {
        return this.value;
    }
}
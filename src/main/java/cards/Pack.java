package cards;

/**
 * Class for the initial pack of cards which are then
 * dealt to players and their adjacent decks.
 * The class is not synchronized as it is only used in
 * initialization.
 */
public class Pack extends Deck {
    private final int size;

    /**
     * Initialize the pack of cards.
     * @param size the number of cards.
     */
    public Pack() {

    }
}

package cards;
import cards.exceptions.InvalidCardException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Hand {
    /**
     * Use ArrayList so we can see all cards at once and discard
     * from any index. Tried making this a subclass of Deck but
     * turned out to be inefficient because ArrayDeque is optimized
     * for taking from the ends of the deque.
     */
    private final ArrayList<Card> cards;
    private final int handId;
    // https://www.baeldung.com/java-concurrent-locks

    public Hand(int handId) {
        this.handId = handId;
        this.cards = new ArrayList<Card>();
        this.lock = new ReentrantLock();
    }

    /**
     * Get number of cards currently in the deck.
     * @return int
     */
    private int getSize() {
        return this.cards.size();
    }

    public int getHandId() {
        return handId;
    }

    /**
     * Removes a card form the hand with a specific cardId and
     * returns that card.
     * @param cardId int
     * @return Card
     * @throws InvalidCardException
     */
    public Card drawCard(int cardId) throws InvalidCardException {
        // Iterate through hand until the card with cardId is found.
        for (int i=0; i<this.getSize(); i++) {
            if (this.cards.get(i).getCardId() == cardId) {
                // Remove the card from cards and return it.
                return this.cards.remove(i);
            }
        }
        throw new InvalidCardException(cardId);
    }
}

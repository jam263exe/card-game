package cards;
import java.util.ArrayDeque;
import java.util.concurrent.locks.ReentrantLock;

public class Deck {

    private final ArrayDeque<Card> cards;
    /**
     * CHANGE OF PLANS - we are no longer using LinkedBlockingQueue
     * as we need discard and take to be TOGETHER one atomic action.
     * We're going to use ArrayDeque - essentially a double-ended
     * queue which allows us to add and remove from the front and
     * back in O(1) time:
     * <a href="https://www.geeksforgeeks.org/java/arraydeque-in-java/">ref</a>
     * We will manually lock both decks involved in the take/discard
     * transaction now as this is not inherently thread-safe.
     */
    private final int deckId;
    // https://www.baeldung.com/java-concurrent-locks
    private ReentrantLock lock;

    public Deck(int deckId) {
        this.deckId = deckId;
        this.cards = new ArrayDeque<Card>();
        this.lock = new ReentrantLock();
    }

    /**
     * Return the lock so we can lock/unlock the Deck object in the
     * player's thread in CardGame. We can't lock it from inside
     * this class as the player thread needs exclusive access to
     * both the deck to their left and to their right.
     * @return ReentrantLock
     */
    public ReentrantLock getLock() {
        return lock;
    }

    /**
     * Takes the top card from the deck.
     * @return Card
     */
    public Card drawCard() {
        return this.cards.removeFirst();
    }

    /**
     * Add a card to the top of the deck
     * @param card Card
     */
    public void addCard(Card card) {
        this.cards.addLast(card);
    }
}

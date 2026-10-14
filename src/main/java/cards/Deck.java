package cards;
import java.util.concurrent.LinkedBlockingQueue;

public class Deck {

    LinkedBlockingQueue<Card> cards;
    /**
     * Use this type of thread safe queue:
     * <a href="https://www.geeksforgeeks.org/java/linkedblockingqueue-class-in-java/">ref</a>
     * We are using offer and take rather than add and remove here
     * as they wait until the queue enters a valid state for the operation
     * to run rather than throwing an exception (eg. another player empties
     * the deck, and now we need to wait until there is another card in the
     * deck before we can take a card.)
     */
    public Deck(int deckId) {
        cards = new LinkedBlockingQueue<Card>();
    }

    public Card drawCard() {

    }

    /**
     * Get integer values of all the cards in this deck.
     */
    public getCards()
}

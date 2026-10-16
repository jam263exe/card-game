package cards;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.List;
import java.util.ArrayList;

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
        return cards.take(); // thread safe, take blocks if empty
    }

    public void addCard(Card card) {
        cards.offer(card); // thread safe, offer blocks if full
    }

    /**
     * Get integer values of all the cards in this deck.
     */
    public List<Integer> getCards() {
        List<Integer> cardValues = new ArrayList<>(); // create a returnable empty list
        for (Card card : cards) {
            cardValues.add(card.getValue()); // add the value of each card to the list
        }
        return cardValues;
    }
}

package cards;
import cards.exceptions.GameAlreadyWonException;
import cards.exceptions.InvalidCardException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;


public class Hand {
    /**
     * Use ArrayList so we can see all cards at once and discard
     * from any index. Tried making this a subclass of Deck but
     * turned out to be inefficient because ArrayDeque is optimized
     * for taking from the ends of the deque.
     */
    private final ArrayList<Card> cards;

    public Hand() {
        this.cards = new ArrayList<Card>();
    }

    /**
     * Get number of cards currently in the deck.
     * @return int
     */
    private int getSize() {
        return this.cards.size();
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

    /**
     * Add a card to the hand. Position is irrelevant.
     * @param card Card
     */
    public void addCard(Card card) {
        this.cards.add(card);
    }

    /**
     * Keep this function in Hand - whether a hand is a winning one
     * or not is a property of the Hand class not the player class.
     */
    public boolean checkWin() {
        int firstCardVal = this.cards.getFirst().getValue();
        for (int i=0; i<this.getSize(); i++) {
            if (this.cards.get(i).getValue() != firstCardVal) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the cardId of a card from the hand, which is of a different
     * denomination to the preferred one and randomly chooses from the unpreferred list.
     * @param preferredDenomination integer value denoting the preferred
     *                              card denomination.
     * @return a cardId.
     */
    private final Random random = new Random();

    public int getUnpreferredCardId(int preferredDenomination) {
        List<Card> unpreferredCards = new ArrayList<>();
        for (Card card : this.cards) {
            if (card.getValue() != preferredDenomination) {
                unpreferredCards.add(card);
            }
        }
        if (!unpreferredCards.isEmpty()) {
            int randomIndex = this.random.nextInt(unpreferredCards.size());
            return unpreferredCards.get(randomIndex).getCardId();
        }
        throw new GameAlreadyWonException();
    }

    // Lets Player access cards to print as string
    @Override
    public String toString() {
        StringBuilder handString = new StringBuilder();
        for (Card c : this.cards) {
            handString.append(c.getValue()).append(" ");
        }
        return handString.toString().trim();
    }
}

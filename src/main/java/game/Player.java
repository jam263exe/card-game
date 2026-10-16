package game;
import cards.Hand;
import cards.Deck;
import cards.Card;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Player {
    private final int playerId;
    private final Hand hand;
    
    public Player(int playerId) {
        this.playerId = playerId;
        this.hand = new Hand();
    }

    /**
     * Add a card to the player's hand.
     * @param card
     */
    public addCardToHand(Card card) {
        hand.addCard(card);
    }


    private Card discardUnpreferredCard() {
        List<Card> unpreferredCards = new ArrayList<>();
        for (Card card : hand) {
            if (card.getValue() != playerId) { // all cards not equal to playerId, put into list
                unpreferredCards.add(card);
            }
        }
        if (!unpreferredCards.isEmpty()) {
            // discard a random unpreferred card, not of playerId
            // this is required by spec to ensure game doesnt stagnate 
            int randomIndex = random.nextInt(unpreferredCards.size());
            Card cardToDiscard = unpreferredCards.get(randomIndex);
            hand.remove(cardToDiscard);
            return cardToDiscard;
        }

    }
}

package game;
import cards.Hand;
import cards.Deck;
import cards.Card;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Player {
    private final int playerId;
    private Hand hand;
    
    public Player(int playerId) {
        this.playerId = playerId;
        this.hand = new Hand();
    }

    public applyDecks(Deck left, Deck right) {

    }

    public addCardToHand(Card card) {
        // add a card to the player's hand
        hand.add(card);
    }

    //encapsulation below, player access only
    private checkWin() { 
        if (hand.size() != 4) return false; // must have 4 cards at all times
        int firstCard = hand.get(0).getValue();
        for (Card card : hand) {
            if (card.getValue() != firstCard) return false; // iterates through hand, if any card is different, player has not won
        }
        return true;
    }

    private Random random = new Random();

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

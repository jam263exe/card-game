package game;
import cards.Card;
import cards.Deck;
import cards.Hand;


public class Player implements Runnable {
    private final int playerId;
    private final Hand hand;
    private final Deck drawDeck;
    private final Deck discardDeck;

    public Player(int playerId, Deck drawDeck, Deck discardDeck) {
        this.playerId = playerId;
        this.hand = new Hand();
        this.drawDeck = drawDeck;
        this.discardDeck = discardDeck;
    }

    public int getPlayerId() {
        return this.playerId;
    }

    private void acceptCard(Card card) {
        this.hand.addCard(card);
    }

    private Card discardCard(int cardId) {
        return hand.drawCard(cardId);
    }

    private atomicDrawDiscard(int cardId) {

    }

    @Override
    public void run() {

    }
}

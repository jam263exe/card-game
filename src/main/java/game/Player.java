package game;
import cards.Card;
import cards.Deck;
import cards.Hand;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;


public class Player implements Runnable {
    private final int playerId;
    private final Hand hand;
    private final Deck drawDeck;
    private final Deck discardDeck;
    private final AtomicBoolean gameOver;
    private final ReentrantLock drawLock;
    private final ReentrantLock discardLock;

    public Player(int playerId, Deck drawDeck, Deck discardDeck, AtomicBoolean gameOver) {
        this.playerId = playerId;
        this.hand = new Hand();
        this.drawDeck = drawDeck;
        this.discardDeck = discardDeck;
        this.gameOver = gameOver;
        this.drawLock = drawDeck.getLock();
        this.discardLock = discardDeck.getLock();
    }

    /**
     * Getter for playerId.
     * @return the player's playerId.
     */
    public int getPlayerId() {
        return this.playerId;
    }

    /**
     * Accept a card by adding it to the hand.
     * @param card the card to accept.
     */
    private void acceptCard(Card card) {
        this.hand.addCard(card);
    }

    /**
     *
     * @param cardId cardId of the card to discard from the hand.
     * @return the specific card withdrawn from the hand.
     */
    private Card discardCard(int cardId) {
        return this.hand.drawCard(cardId);
    }

    /**
     * Draw and discard in one atomic action. it is important that
     * it executes in the order of getting the next unpreferred cardId,
     * then withdrawing it, then finally adding another card to the
     * hand. Other orders would cause issues with the cardId due to
     * the hand size changing.
     */
    private void atomicDrawDiscard() {
        int unpreferredCardId = this.hand.getUnpreferredCardId(this.playerId);
        this.discardDeck.addCard(this.hand.drawCard(unpreferredCardId));
        this.hand.addCard(drawDeck.drawCard());
    }

    @Override
    public void run() {
        while (!gameOver.get()) {
            try {
                this.drawLock.lock();
                this.discardLock.lock();
                this.atomicDrawDiscard();
            }
            finally {
                this.drawLock.unlock();
                this.discardLock.unlock();
            }
        }
    }
}

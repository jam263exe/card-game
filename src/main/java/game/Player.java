package game;
import cards.Card;
import cards.Deck;
import cards.Hand;
import java.io.FileWriter;
import java.io.IOException;
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
    public void acceptCard(Card card) {
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
     * then discarding it, then finally adding another card to the
     * hand. Other orders would cause issues with the cardId due to
     * the hand size changing.
     */
    private void atomicDrawDiscard() {

        // Prevent drawing from empty decks
        if (this.drawDeck.isEmpty()) {
            return;
        }

        // Discard unpreferred card
        int unpreferredCardId = this.hand.getUnpreferredCardId(this.playerId);
        Card discardedCard = this.hand.drawCard(unpreferredCardId);
        this.discardDeck.addCard(discardedCard);

        // Draw card
        Card drawnCard = this.drawDeck.drawCard();
        this.hand.addCard(drawnCard);


        // Log actions to file
        logToFile("player " + this.playerId + " draws a " + drawnCard.getValue() + " from deck " + this.drawDeck.getDeckId());
        logToFile("player " + this.playerId + " discards a " + discardedCard.getValue() + " to deck " + this.discardDeck.getDeckId());
        logToFile("player " + this.playerId + " current hand is " + this.hand.toString());
    }

    @Override
    public void run() {
        logToFile("player " + this.playerId + " initial hand " + this.hand.toString());

        // Check for win
        if (this.hand.checkWin()) {
            logToFile("player " + this.playerId + " wins");
            gameOver.set(true);
            return;
        }
        while (!this.gameOver.get()) {
            // Order locks by deckId in ascending order
            ReentrantLock firstLock;
            ReentrantLock secondLock;

            if (this.drawDeck.getDeckId() < this.discardDeck.getDeckId()) {
                firstLock = this.drawLock;
                secondLock = this.discardLock;
            } else {
                firstLock = this.discardLock;
                secondLock = this.drawLock;
            }

            firstLock.lock();
            secondLock.lock();

            try {
                if (this.gameOver.get()) {
                    break;
                }

                // Safeguard against empty deck exceptions
                if (this.drawDeck.isEmpty()) {
                    this.gameOver.set(true);
                    break;
                }

                this.atomicDrawDiscard();

                if (this.hand.checkWin()) {
                    logToFile("player " + this.playerId + " wins");
                    System.out.println("player " + this.playerId + " wins");
                    this.gameOver.set(true);
                    break;
                }
            }
            finally {
                secondLock.unlock();
                firstLock.unlock();
            }
        }

        // Game over, so log final hand
        if (!this.hand.checkWin()) {
            logToFile("player " + this.playerId + " has been informed that another player has won");
        }
        logToFile("player " + this.playerId + " exits");
        logToFile("player " + this.playerId + " final hand: " + this.hand.toString());
        
    }

     /**
     * Generates fileName based on the thread executing as required by spec.
     * Uses FileWriter and true argument to append the text to end of
     * the file. It writes the passed message string, newline and closes file.
     * Located in Player class as it is a property of the player to log their actions.
     */

    private void logToFile(String message) {
        try {
            String fileName = "player" + this.playerId + "_output.txt";
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(message + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

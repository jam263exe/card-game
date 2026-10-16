package game;
import cards.Deck;
import java.lang.Thread;

public class CardGame {

    private volatile boolean gameOver = false;
    // Number of players (referred to as n in comments)
    private final int numPlayers;
    // Initial collection of all 8n cards
    private Deck pack;
    private Thread[] threads;

    public CardGame(int numPlayers) {
        this.numPlayers = numPlayers;
        this.threads = new Thread[numPlayers];

        threads[0].start();
    }

    private void initializeThreads() {
        this.threads = new Thread[this.numPlayers];
    }

    /**
     * Give cards to players in a round-robin fashion.
     */
    private void initializePlayers() {

    }

    /**
     * Give cards to decks in round-robin fashion AFTER
     * giving cards to players.
     */
    private void initializeDecks() {

    }

    /**
     * Discard and take must be treated as ONE atomic action according
     * to the spec (annoyingly) so we manage both from this function.
     * Access to the deck and hand in question needs to be blocked for
     * other threads while both actions occur.
     */
    private void swapCards() {

    }
}

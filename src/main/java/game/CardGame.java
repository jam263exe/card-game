package game;
import cards.Deck;
import cards.Card;

public class CardGame {

    private volatile boolean gameOver = false;
    // Number of players (referred to as n in comments)
    private final int numPlayers;
    // Initial collection of all 8n cards
    private Deck pile;

    public CardGame(int numPlayers) {
        this.numPlayers = numPlayers;
        pile = new Deck(numPlayers);
        this.initialisePlayers();
        this.initialiseDecks();
    }

    /**
     * Give cards to players in a round-robin fashion.
     */
    private void initialisePlayers() {

    }

    /**
     * Give cards to decks in round-robin fashion AFTER
     * giving cards to players.
     */
    private void initialiseDecks() {

    }
}

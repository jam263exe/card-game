package game;

import cards.Deck;
import cards.Card;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    Deck drawDeck;
    Deck discardDeck;
    Player player;
    AtomicBoolean gameOver;

    public PlayerTest() {
        this.gameOver = new AtomicBoolean(false);
        this.drawDeck = new Deck(0);
        this.discardDeck = new Deck(1);

        drawDeck.addCard(new Card(3, 0));
        drawDeck.addCard(new Card(1, 1));
        drawDeck.addCard(new Card(1, 2));
        drawDeck.addCard(new Card(5, 3));
        drawDeck.addCard(new Card(8, 4));

        discardDeck.addCard(new Card(2, 5));
        discardDeck.addCard(new Card(0, 6));
        discardDeck.addCard(new Card(4, 7));

        this.player = new Player(0, drawDeck, discardDeck, gameOver);
    }

    @Test
    public void acceptCardTest() {
        // Player starts with an empty hand and draws the top
        // card (3) from drawDeck.
        this.player.acceptCard(this.drawDeck.drawCard());
        assertEquals("3", this.player.toString());
    }
}

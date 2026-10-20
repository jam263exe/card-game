package cards;

import org.junit.jupiter.api.Test;
import cards.Card;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeckTest {
    private Deck deck;
    public DeckTest() {
        this.deck = new Deck(0);
        // Add cards to deck.
        // Adds them to the bottom of the deck each
        // addCard() call.
        this.deck.addCard(new Card(2, 0));
        this.deck.addCard(new Card(0, 1));
        this.deck.addCard(new Card(3, 2));
        this.deck.addCard(new Card(1, 3));
    }

    @Test
    public void drawCardTest() {
        // Should draw the card from the top of the deck.
        // Because addCard should add to the bottom of the deck
        // each time, we should draw the last card added
        // which is cardId 0 with value 2.
        Card testCard = this.deck.drawCard();
        assertEquals(2, testCard.getValue());
        assertEquals(0, testCard.getCardId());
    }

    @Test
    public void testIsEmpty() {
        // Create empty deck
        Deck emptyDeck = new Deck(1);
        assertTrue(emptyDeck.isEmpty());
    }

    @Test
    public void testToString() {
        // Should return "2 0 3 1"
        assertEquals("2 0 3 1", this.deck.toString());
    }
}

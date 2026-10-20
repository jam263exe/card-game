package cards;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class HandTest {
    private Hand hand;
    private Hand winningHand;

    // addCard is inadvertently tested here
    public HandTest() {
        // This creates the hand 0 2 3 1
        this.hand = new Hand();
        this.hand.addCard(new Card(0, 0));
        this.hand.addCard(new Card(2, 1));
        this.hand.addCard(new Card(3, 2));
        this.hand.addCard(new Card(1, 3));

        // Creates a winning hand (all 3s)
        this.winningHand = new Hand();
        for (int i=0; i<4; i++) {
            this.winningHand.addCard(new Card(3, i));
        }
    }

    @Test
    public void toStringTest() {
        String handString = this.hand.toString();
        assertEquals("0 2 3 1", handString);
    }

    @Test
    public void drawCardTest() {
        // cardId 2 should have value 3.
        assertEquals(3, this.hand.drawCard(2).getValue());
    }

    @Test
    public void getUnpreferredCardTest() {
        // Repeat 100 times. If preferred card is 1 then
        // we should never get cardId 1 (the value is also 1.)
        int oneIdCount = 0;
        for (int i=0; i<100; i++) {
            if (hand.getUnpreferredCardId(1) == 1) {
                    oneIdCount++;
            }
        }
        assertEquals(0, oneIdCount);
    }

    @Test
    public void checkWinTest() {
        assertTrue(this.winningHand.checkWin());
    }
}

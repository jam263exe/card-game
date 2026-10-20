package cards;
import org.junit.jupiter.api.Test;

public class CardGameTest {
    @Test
    public void testOutput() {
        // Manually analyse output files to ensure
        // it is behaving normally

        /*
        RAN WITH PACK FILE pack-test.txt IN ROOT DIRECTORY WITH
        n=4 AND OUTPUT "Player 3 wins"
        ------------------------pack-test.txt------------------------
        0
        1
        2
        1
        3
        3
        0
        2
        0
        2
        1
        3
        3
        2
        3
        1
        2
        0
        2
        0
        2
        1
        1
        3
        3
        2
        0
        1
        3
        1
        2
        2
        ----------------------------------------------------------------

        AFTER RUNNING HERE ARE THE OUTPUT CONTENTS:
        -------------------deck0_output.txt-----------------------------
        deck0 contents: 2 3 2 0
        -------------------deck1_output.txt-----------------------------
        deck1 contents: 3 1 1 0 1
        -------------------deck2_output.txt-----------------------------
        deck2 contents: 2 1 1 0 2 2 2
        -------------------deck3_output.txt-----------------------------
        deck3 contents:

        This is good - all of the cards in pack-test.txt are there and none
        have gone missing or been duplicated.

        --------------------player0_output.txt--------------------------
        player 0 initial hand 0 3 0 3
        player 0 draws a 2 from deck 0
        player 0 discards a 2 to deck 3
        player 0 current hand is 0 3 0 3
        player 0 draws a 2 from deck 0
        player 0 discards a 3 to deck 3
        player 0 current hand is 0 0 3 2
        player 0 draws a 3 from deck 0
        player 0 discards a 2 to deck 3
        player 0 current hand is 0 0 3 3
        player 0 draws a 3 from deck 0
        player 0 discards a 3 to deck 3
        player 0 current hand is 0 0 3 3
        player 0 has been informed that another player has won
        player 0 exits
        player 0 final hand: 0 0 3 3

         */

    }

    @Test
    public void testEfficiency() {
        // Test how total runtime scales as n increases
    }
}

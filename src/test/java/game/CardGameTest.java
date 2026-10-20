package game;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

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
        deck0 contents: 3 3 3 2 2
        -------------------deck1_output.txt-----------------------------
        deck1 contents: 1 0 3 1
        -------------------deck2_output.txt-----------------------------
        deck2 contents: 2 1 1 0 2 2 2
        -------------------deck3_output.txt-----------------------------
        deck3 contents:

        This is good - all of the cards in pack-test.txt are there and none
        have gone missing or been duplicated.

        --------------------player0_output.txt--------------------------
        player 0 initial hand 0 3 0 3
        player 0 draws a 2 from deck 0
        player 0 discards a 3 to deck 3
        player 0 current hand is 0 0 3 2
        player 0 draws a 2 from deck 0
        player 0 discards a 3 to deck 3
        player 0 current hand is 0 0 2 2
        player 0 has been informed that another player has won
        player 0 exits
        player 0 final hand: 0 0 2 2

        --------------------player1_output.txt--------------------------
        player 1 initial hand 1 3 2 2
        player 1 draws a 0 from deck 1
        player 1 discards a 3 to deck 0
        player 1 current hand is 1 2 2 0
        player 1 draws a 1 from deck 1
        player 1 discards a 2 to deck 0
        player 1 current hand is 1 2 0 1
        player 1 draws a 2 from deck 1
        player 1 discards a 2 to deck 0
        player 1 current hand is 1 0 1 2
        player 1 has been informed that another player has won
        player 1 exits
        player 1 final hand: 1 0 1 2

        --------------------player2_output.txt--------------------------
        player 2 initial hand 2 0 1 3
        player 2 draws a 2 from deck 2
        player 2 discards a 0 to deck 1
        player 2 current hand is 2 1 3 2
        player 2 draws a 1 from deck 2
        player 2 discards a 3 to deck 1
        player 2 current hand is 2 1 2 1
        player 2 draws a 0 from deck 2
        player 2 discards a 1 to deck 1
        player 2 current hand is 2 2 1 0
        player 2 has been informed that another player has won
        player 2 exits
        player 2 final hand: 2 2 1 0

        --------------------player3_output.txt--------------------------
        player 3 initial hand 1 2 3 1
        player 3 draws a 0 from deck 3
        player 3 discards a 2 to deck 2
        player 3 current hand is 1 3 1 0
        player 3 draws a 3 from deck 3
        player 3 discards a 1 to deck 2
        player 3 current hand is 1 3 0 3
        player 3 draws a 1 from deck 3
        player 3 discards a 1 to deck 2
        player 3 current hand is 3 0 3 1
        player 3 draws a 2 from deck 3
        player 3 discards a 1 to deck 2
        player 3 current hand is 3 0 3 2
        player 3 draws a 3 from deck 3
        player 3 discards a 0 to deck 2
        player 3 current hand is 3 3 2 3
        player 3 draws a 3 from deck 3
        player 3 discards a 2 to deck 2
        player 3 current hand is 3 3 3 3
        player 3 wins
        player 3 exits
        player 3 final hand: 3 3 3 3
        -------------------------------------------------------------

        ALL OUTPUT APPEARS EXPECTED. DRAW AND DISCARD BEHAVIOUR
        WORK NORMALLY AND WINNING CORRECTLY STOPS THE OTHER THREADS.
         */

    }

    @Test
    public void testEfficiency() {
        Random rng = new Random();
        String packFilename = "efficiency-test-pack.txt";
        String csvFilename = "execution-time-test.csv";

        FileWriter csvWriter = null;
        try {
            csvWriter = new FileWriter(csvFilename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            csvWriter.write("n, exec-time-ms\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int n = 1; n < 100; n++) {
            try {
                // Create test packs with random numbers
                FileWriter packWriter = new FileWriter(packFilename);
                for (int i=0; i<8*n; i++) {
                    packWriter.write(rng.nextInt(n + 1) + "\n");
                }
                packWriter.close();

                // Get execution time
                long startTime = System.currentTimeMillis();
                CardGame game = new CardGame(n, packFilename);
                game.startGame();
                long duration = System.currentTimeMillis() - startTime;

                // Log to csv to be used in pyplot scattergraph
                csvWriter.write(n + "," + duration + "\n");
                System.out.println("Players (n=" + n + "): " + duration + " ms");

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        try {
            csvWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Results saved to " + csvFilename);
    }
}

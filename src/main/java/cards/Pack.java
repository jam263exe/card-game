package cards;

import cards.exceptions.InvalidPackException;
import cards.exceptions.PackInitializationError;
import game.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Pack extends Deck {

    public Pack(int numPlayers, String path) throws
            InvalidPackException,
            PackInitializationError,
            FileNotFoundException
    {
        super(-1);
        this.loadFromFile(path, numPlayers);
    }

    // OLD DOCSTRING STILL MOSTLY RELEVANT
    // Takes a fileName string and opens it using {@link java.io.FileDescriptor}
    // and reads it line by line using {@link java.util.Scanner}
    // Each line it checks if int is negative and throws IllegalArgumentException if it is.
    // It converts valid ints to card objects giving unique cardIds using a counter
    // and adds them to the pack. It then check if 8n cards were loaded.
    // if not error thrown.
    /**
     * Creates a pack of cards by loading from a file.
     * @param path In this implementation the file will be
     *             in the same directory as where the
     *             program is executing so path is just the
     *             file name (String).
     * @param numPlayers The number of players in the game.
     *                   Throws an error if the pack file
     *                   does not contain this amount of
     *                   cards.
     */
    private void loadFromFile(String path, int numPlayers) throws
            InvalidPackException,
            PackInitializationError,
            FileNotFoundException {
        File file = new File(path);
        Scanner scanner = null;

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }

        int expectedCards = 8 * numPlayers;
        int cardCounter = 0;

        while (scanner.hasNextInt()) {
            int value = scanner.nextInt();
            if (value < 0) {
                scanner.close();
                throw new InvalidPackException();
            }

            // Adds card to pack
            this.addCard(new Card(value, cardCounter));
            cardCounter++;
        }
        scanner.close();

        if (cardCounter != expectedCards) {
            throw new PackInitializationError();
        }
    }
}

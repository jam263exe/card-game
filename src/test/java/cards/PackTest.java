package cards;

import cards.exceptions.PackInitializationException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PackTest {
    // The pack class inherits from the deck class, so
    // most of its functions are tested in DeckTest.
    public PackTest() {

    }

    @Test
    public void testInvalidPath() {
        // Test providing an invalid file path throws FileNotFoundException
        assertThrows(FileNotFoundException.class, () -> {
            new Pack(4, "non-existent-pack.txt");
        });
    }

    @Test
    public void testLoadFromFile() {
        try {
            Pack pack = new Pack(4, "pack-test.txt");
            System.out.println(pack.toString());
        } catch (FileNotFoundException e) {
            // Handle or fail the test if the file is missing
            org.junit.jupiter.api.Assertions.fail("Pack not found!!");
        }
    }

    @Test
    public void testWrongNumPlayers() {
        // Should throw PackInitializationException
        assertThrows(PackInitializationException.class, () -> {
            Pack pack = new Pack(3, "pack-test.txt");
        });
    }
}
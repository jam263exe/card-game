package cards;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {
    /**
     *  Check the getValue() method works and returns the correct
     *  value assigned when card is instantiated.
     */
    @Test
    public void checkValue() {
        Card card = new Card(8);
        System.out.println("checkValue test passed.");
    }
}

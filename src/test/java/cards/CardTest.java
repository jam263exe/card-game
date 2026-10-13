package cards;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {
    @Test
    public void Test1() {
        Card card = new Card(8);
        assertEquals(8, card.value);
    }
    @Test
    public void Test2() {
        Card card = new Card(8);
        assertEquals(7, card.value);
    }
}

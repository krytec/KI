import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class TestNode {
    @Test
    public void getHFromNodeTest(){
        int[] puzzle = {
                1, 2, 3,
                4, 5, 6,
                7, 0, 8
        };
        int[] goal = {
                1, 2, 3,
                8, 0, 4,
                7, 6, 5
        };
        Node root = new Node(puzzle);
        int expected = 10;
        int actual = root.getH("h1");
        assertEquals(expected,actual);
        expected = 5;
        actual = root.getH("h2");
        assertEquals(expected,actual);
        expected = 4+4;
        actual = root.getH("h3");
        assertEquals(expected,actual);

    }
}

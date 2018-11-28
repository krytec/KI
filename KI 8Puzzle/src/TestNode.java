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
                4, 5, 6,
                7, 8, 0
        };
        Node root = new Node(puzzle);
        int expected = 2;
        int actual = root.getH();
        assertEquals(expected,actual);


    }
}

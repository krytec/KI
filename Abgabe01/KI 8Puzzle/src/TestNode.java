import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestNode {

    /**
     * Test für die heuristischen Funktionen, um zu schauen, ob sie erwartete Werte liefern.
     */
    @Test
    public void getHFromNodeTest() {
        int[] puzzle = {
                1, 2, 3,
                4, 5, 6,
                7, 0, 8
        };

        Node root = new Node(puzzle);
        int expected = 10;
        int actual = root.getH("h1"); // Manhattan-Distanz für jede Zahl aufsummiert
        assertEquals(expected, actual);
        expected = 5;
        actual = root.getH("h2"); // Anzahl von Zahlen, die sich auf einer falschen Position befinden
        assertEquals(expected, actual);
        expected = 4 + 4;
        actual = root.getH("h3"); // Anzahl von falschen Positionierungen bezüglich Spalten und Reihen
        assertEquals(expected, actual);

    }
}

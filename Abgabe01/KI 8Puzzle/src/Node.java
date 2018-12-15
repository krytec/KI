import java.util.ArrayList;
import java.util.List;

/**
 * Eine Datenstruktur für einen 8-Puzzle Zustand. Enthält den aktuellen Zustand, den vorherigen Zustand,
 * die nachfolgenden Zustände, die Pfadlänge vom Initialzustand zu dem aktuellen Zustand, Methoden zur Bestimmung
 * der nachfolgenden Zuständen (Operatoren) und 3 verschiedene heuristische Funktionen zur Bestimmung von H.
 */
public class Node {

    public List<Node> children = new ArrayList<>();
    public Node parent;
    public int[] puzzle;
    private int x = 0;
    private int col = 3;
    private int g = 0;

    static int[] goal = {
            1, 2, 3,
            8, 0, 4,
            7, 6, 5
    };

    /**
     * Konstruktor für ein 8-Puzzle
     *
     * @param p - int-Array, der eine 8-Puzzle-Konfiguration repräsentiert.
     */
    public Node(int[] p) {
        puzzle = p;
    }

    /**
     * Verschiebt die Null nach rechts, wenn es möglich ist.
     *
     * @param p - int-Array, der eine 8-Puzzle-Konfiguration repräsentiert.
     * @param i - Index der aktuellen Position von 0 in dem 8-Puzzle-Array p.
     */
    public void moveRight(int[] p, int i) {
        if (i % col < col - 1) {
            int[] pc = new int[9];
            copyPuzzle(pc, p);
            int temp = pc[i + 1];
            pc[i + 1] = pc[i];
            pc[i] = temp;
            Node child = new Node(pc);
            children.add(child);
            child.parent = this;
            child.setG(this.getG() + 1);
        }
    }

    /**
     * Verschiebt die Null nach links, wenn es möglich ist.
     *
     * @param p - int-Array, der eine 8-Puzzle-Konfiguration repräsentiert.
     * @param i - Index der aktuellen Position von 0 in dem 8-Puzzle-Array p.
     */
    public void moveLeft(int[] p, int i) {
        if (i % col > 0) {
            int[] pc = new int[9];
            copyPuzzle(pc, p);
            int temp = pc[i - 1];
            pc[i - 1] = pc[i];
            pc[i] = temp;
            Node child = new Node(pc);
            children.add(child);
            child.parent = this;
            child.setG(this.getG() + 1);
        }
    }

    /**
     * Verschiebt die Null nach oben, wenn es möglich ist.
     *
     * @param p - int-Array, der eine 8-Puzzle-Konfiguration repräsentiert.
     * @param i - Index der aktuellen Position von 0 in dem 8-Puzzle-Array p.
     */
    public void moveUp(int[] p, int i) {
        if (i - col >= 0) {
            int[] pc = new int[9];
            copyPuzzle(pc, p);
            int temp = pc[i - 3];
            pc[i - 3] = pc[i];
            pc[i] = temp;
            Node child = new Node(pc);
            children.add(child);
            child.parent = this;
            child.setG(this.getG() + 1);
        }
    }

    /**
     * Verschiebt die Null nach unten, wenn es möglich ist.
     *
     * @param p - int-Array, der eine 8-Puzzle-Konfiguration repräsentiert.
     * @param i - Index der aktuellen Position von 0 in dem 8-Puzzle-Array p.
     */
    public void moveDown(int[] p, int i) {
        if (i + col < puzzle.length) {
            int[] pc = new int[9];
            copyPuzzle(pc, p);
            int temp = pc[i + 3];
            pc[i + 3] = pc[i];
            pc[i] = temp;
            Node child = new Node(pc);
            children.add(child);
            child.parent = this;
            child.setG(this.getG() + 1);
        }
    }

    /**
     * Bestimmt die Liste von Nachfolger-Knoten für den aktuellen Knoten
     * unter Verwendung von RIGHT-, LEFT-, UP-, DOWN-Operatoren
     */
    public void expandNode() {
        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] == 0) {
                x = i;
                break;
            }
        }
        moveDown(puzzle, x);
        moveLeft(puzzle, x);
        moveRight(puzzle, x);
        moveUp(puzzle, x);
    }

    /**
     * Erzeugt eine Kopie von einer 8-Puzzle-Konfiguration.
     *
     * @param a - Source-int-Array, der eine 8-Puzzle-Konfiguration repräsentiert.
     * @param b - Ziel-int-Array, der eine 8-Puzzle-Konfiguration repräsentiert.
     */
    public void copyPuzzle(int[] a, int[] b) {
        for (int i = 0; i < a.length; i++) {
            a[i] = b[i];
        }
    }

    /**
     * Überprüft, ob die aktuelle 8-Puzzle-Konfiguration der Goal-Konfiguration entspricht.
     *
     * @return true, wenn die aktuelle 8-Puzzle-Konfiguration der Goal-Konfiguration entspricht. Ansonsten false.
     */
    public boolean goalTest() {
        return this.isSamePuzzle(goal);
    }

    /**
     * Liefert den H-Wert für den aktuellen Knoten unter Verwendung einer bestimmten Heuristik.
     *
     * @param heuristic - String, mit der Name der Heuristik, die verwendet werden soll.
     * @return int - der H-Wert für den aktuellen Knoten.
     */
    public int getH(String heuristic) {
        int h = 0;
        switch (heuristic) {

            case "h1": // Manhattan-Distanz für jede Zahl aufsummiert
                int[] from;
                int[] to;
                for (int i = 0; i < puzzle.length; i++) {
                    from = getPositionInGrid(puzzle[i], puzzle);
                    to = getPositionInGrid(puzzle[i], goal);
                    h += (Math.abs(to[0] - from[0]) + Math.abs(to[1] - from[1]));
                }
                break;

            case "h2": // Anzahl von Zahlen, die sich auf einer falschen Position befinden
                for (int i = 0; i < puzzle.length; i++) {
                    if (puzzle[i] != goal[i]) {
                        h++;
                    }
                }
                break;
            case "h3": // Anzahl von falschen Positionierungen bezüglich Spalten und Reihen
                for (int i = 0; i < puzzle.length; i++) {
                    if (!inCol(i, puzzle[i])) {
                        h++;
                    }
                    if (!inRow(i, puzzle[i])) {
                        h++;
                    }
                }
        }
        return h;
    }

    /**
     * Überprüft ob eine Zahl sich in der richtigen Reihe bezüglich der Ziel-Konfiguration befindet.
     *
     * @param i      - Index-Position der search-Zahl in einem 8-Puzzle.
     * @param search - eine Zahl aus einem 8-Puzzle (0-8).
     * @return true, wenn die Zahl sich bereits in der richtigen Reihe befindet. Ansonsten false.
     */
    public boolean inRow(int i, int search) {
        if (i == 0 || i == 1 || i == 2) {
            for (int j = 0; j < 3; j++) {
                if (goal[j] == search) {
                    return true;
                }
            }
            return false;
        } else if (i == 3 || i == 4 || i == 5) {
            for (int j = 3; j < 6; j++) {
                if (goal[j] == search) {
                    return true;
                }
            }
            return false;
        } else if (i == 6 || i == 7 || i == 8) {
            for (int j = 6; j < 9; j++) {
                if (goal[j] == search) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /**
     * Überprüft ob eine Zahl sich in der richtigen Spalte bezüglich der Ziel-Konfiguration befindet.
     *
     * @param i      - Index-Position der search-Zahl in einem 8-Puzzle.
     * @param search - eine Zahl aus einem 8-Puzzle (0-8).
     * @return true, wenn die Zahl sich bereits in der richtigen Spalte befindet. Ansonsten false.
     */
    public boolean inCol(int i, int search) {
        if (i == 0 || i == 3 || i == 6) {
            for (int j = 0; j < 7; j += 3) {
                if (goal[j] == search) {
                    return true;
                }
            }
            return false;
        } else if (i == 1 || i == 4 || i == 7) {
            for (int j = 1; j < 8; j += 3) {
                if (goal[j] == search) {
                    return true;
                }
            }
            return false;
        } else if (i == 2 || i == 5 || i == 8) {
            for (int j = 2; j < 9; j += 3) {
                if (goal[j] == search) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /**
     * Übersetzt ein Index aus einem 1D-Array in XY-Koordinaten in einem 8-Puzzle
     * für die Berechnung von Manhattan-Distanzen.
     *
     * @param i    - Index, der übersetzt werden muss.
     * @param grid - 1D-int-Array, der eine 8-Puzzle-Konfiguration repräsentiert.
     * @return
     */
    public int[] getPositionInGrid(int i, int[] grid) {
        int[] xy = {0, 0};
        for (int j = 0; j < grid.length; j++) {
            if (grid[j] == i) {
                if (j == 0 || j == 1 || j == 2) xy[0] = 1;
                else if (j == 3 || j == 4 || j == 5) xy[0] = 2;
                else if (j == 6 || j == 7 || j == 8) xy[0] = 3;

                if (j == 0 || j == 3 || j == 6) xy[1] = 1;
                else if (j == 1 || j == 4 || j == 7) xy[1] = 2;
                else if (j == 2 || j == 5 || j == 8) xy[1] = 3;
                return xy;
            }
        }
        return xy;
    }

    /**
     * Liefert den G-Wert des aktuellen Knotens.
     *
     * @return g - G-Wert des aktuellen Knotens.
     */
    public int getG() {
        return g;
    }

    /**
     * Setzt den G-Wert des aktuellen Knotens.
     *
     * @param g - G-Wert des aktuellen Knotens als int.
     */
    public void setG(int g) {
        this.g = g;
    }

    /**
     * Überprüft, ob die aktuelle 8-Puzzle-Konfiguration mit einer anderen übereinstimmt.
     *
     * @param p - 8-Puzzle-Konfiguration, mit der vergliechen werden muss.
     * @return true, wenn die aktuelle 8-Puzzle-Konfiguration mit einer anderen übereinstimmt. Ansonsten false.
     */
    public boolean isSamePuzzle(int[] p) {
        boolean samePuzzle = true;
        for (int i = 0; i < p.length; i++) {
            if (puzzle[i] != p[i]) {
                samePuzzle = false;
                break;
            }
        }
        return samePuzzle;
    }

    /**
     * Liefert eine String-Repräsentation der aktuellen 8-Puzzle-Konfiguration.
     *
     * @return String-Repräsentation der aktuellen 8-Puzzle-Konfiguration.
     */
    @Override
    public String toString() {
        StringBuilder line = new StringBuilder("\n");
        int m = 0;
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < col; j++) {
                line.append(" ").append(this.puzzle[m]).append(" ");
                m++;
            }
            line.append("\n");
        }
        return line.toString();
    }
}

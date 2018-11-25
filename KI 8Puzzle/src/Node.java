import java.util.ArrayList;
import java.util.List;

public class Node {

    public List<Node> children = new ArrayList<>();
    public Node parent;
    public int[] puzzle;
    private int x = 0;
    private int col = 3;

    public Node(int[] p) {
        puzzle = p;
    }

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
        }
    }

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
        }
    }

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
        }
    }

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
        }
    }

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

    public void copyPuzzle(int[] a, int[] b) {
        for (int i = 0; i < a.length; i++) {
            a[i] = b[i];
        }
    }

    public boolean goalTest() {
        boolean isGoal = true;
        int[] goal = {
                1, 2, 3,
                8, 0, 4,
                7, 6, 5
        };
        if (!this.isSamePuzzle(goal)) isGoal = false;
        return isGoal;
    }

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

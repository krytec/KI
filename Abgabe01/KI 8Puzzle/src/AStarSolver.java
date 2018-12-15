import java.util.ArrayList;
import java.util.List;

public class AStarSolver {
    public static void main(String[] args) {

        int[] puzzle = {
                2, 8, 3,
                1, 6, 4,
                7, 0, 5
        };

        Node root = new Node(puzzle);

        List<Node> solutionH1 = suche(root, "h1");
        System.out.println("-- A* SEARCH H1 --");
        Solver.printSolution(solutionH1);
        System.out.println();
        Solver.logSolution(solutionH1, "ASTARSEARCH_H1");

        List<Node> solutionH2 = suche(root, "h2");
        System.out.println("-- A* SEARCH H2 --");
        Solver.printSolution(solutionH2);
        System.out.println();
        Solver.logSolution(solutionH2, "ASTARSEARCH_H2");

        List<Node> solutionH3 = suche(root, "h3");
        System.out.println("-- A* SEARCH H3 --");
        Solver.printSolution(solutionH3);
        System.out.println();
        Solver.logSolution(solutionH3, "ASTARSEARCH_H3");
    }

    /**
     * A*-Suche mit hilfe einer einfachen Queue
     *
     * @param root      - Anfangsknoten
     * @param heuristic - Name der Heuristik als String
     * @return List<Node> - Lösungsweg
     */
    public static List<Node> suche(Node root, String heuristic) {
        List<Node> pathToSolution = new ArrayList<>();
        List<Node> openList = new ArrayList<>();

        openList.add(root);

        // solange noch Knoten da sind suchen wir
        while (!openList.isEmpty()) {
            Node currentNode = openList.get(0);

            // den Knoten mit dem geringsten F-Wert wählen
            if (openList.size() > 1) {
                int lowestF = Integer.MAX_VALUE;
                for (Node node : openList) {
                    int currentF = node.getH(heuristic) + node.getG();
                    if (currentF < lowestF) {
                        currentNode = node;
                        lowestF = currentF;
                    }
                }
            }

            // schauen, ob wir bereits den Zielknoten erreicht haben (zB wenn root schon das Ziel ist)
            if (currentNode.goalTest()) {
                System.out.println("Goal found!");
                Solver.pathTrace(pathToSolution, currentNode);
                return pathToSolution;
            }

            openList.remove(currentNode);
            currentNode.expandNode();

            // alle Kinder durchlaufen und der Queue hinzufügen
            for (int i = currentNode.children.size() - 1; i >= 0; i--) {
                Node currentChild = currentNode.children.get(i);

                // schauen, ob wir den Zielknoten erreicht haben
                if (currentChild.goalTest()) {
                    System.out.println("Goal found!");
                    Solver.pathTrace(pathToSolution, currentChild);
                    return pathToSolution;
                }

                openList.add(0, currentChild);
            }
        }

        return pathToSolution;
    }
}

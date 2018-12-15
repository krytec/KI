import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Solver {

    public static void main(String[] args) {

        int[] puzzle = {
                2, 8, 3,
                1, 6, 4,
                7, 0, 5
        };

        Node root = new Node(puzzle);

        List<Node> solutionBFS = suche(root, "breitensuche");
        List<Node> solutionDFS = suche(root, "tiefensuche");

        System.out.println("-- BREITENSUCHE --");
        printSolution(solutionBFS);
        System.out.println();
        logSolution(solutionBFS, "Breitensuche");

        System.out.println("-- TIEFENSUCHE --");
        printSolution(solutionDFS);
        System.out.println();
        logSolution(solutionDFS, "Tiefensuche");
    }

    /**
     * Breitensuche und Tiefensuche mit hilfe einer einfachen Queue
     *
     * @param root      - Anfangsknoten
     * @param algorithm - Name des Algorithmus als String
     * @return List<Node> - Lösungsweg
     */
    public static List<Node> suche(Node root, String algorithm) {
        List<Node> pathToSolution = new ArrayList<>();
        List<Node> openList = new ArrayList<>();
        List<Node> closedList = new ArrayList<>();

        openList.add(root);

        // solange noch knoten da sind suchen wir
        while (!openList.isEmpty()) {

            // abarbeiten des obersten knotens
            Node currentNode = openList.get(0);
            closedList.add(currentNode);
            openList.remove(0);

            // ist dieser knoten das goal?
            if (currentNode.goalTest()) {
                System.out.println("Goal found!");
                pathTrace(pathToSolution, currentNode);
                return pathToSolution;
            }

            // alle richtungen gehen
            currentNode.expandNode();

            // der einzige unterschied ist, wie man die queue (aka openList) bildet
            switch (algorithm) {
                case "breitensuche":
                    //alle children abfangen
                    for (int i = 0; i < currentNode.children.size(); i++) {
                        Node currentChild = currentNode.children.get(i);

                        // currentChild wird an die openList angehängt
                        // Beispiel pathtracing : a
                        //                       b,c,d
                        //                      efg hij klm
                        // erste queue a, dann bcd, dann cdefg dann defghij und so weiter

                        if (!contains(openList, currentChild) && !contains(closedList, currentChild)) {
                            openList.add(currentChild);
                        }
                    }
                    break;
                case "tiefensuche":
                    // alle children immer am ANFANG der openList hinzufügen (Stack-Prinzip)
                    for (int i = currentNode.children.size() - 1; i >= 0; i--) {
                        Node currentChild = currentNode.children.get(i);

                        // Beispiel pathtracing :    a
                        //                       b   c    d
                        //                     efg  hij  klm
                        // queue: a -> bcd -> efgcd -> fgcd -> gcd -> cd -> hijd -> ijd ... usw

                        if (!contains(openList, currentChild) && !contains(closedList, currentChild)) {
                            openList.add(0, currentChild);
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid algorithm name");
                    break;
            }
        }
        return pathToSolution;
    }

    /**
     * Bildet eine Liste mit einem Lösungsweg, indem alle Ancestor-Knoten
     * des übergebenen Knotens rückwärts durchgegangen werden.
     *
     * @param path - Liste, in der der Lösungsweg gespeichert werden soll.
     * @param goal - Start-Knoten.
     */
    public static void pathTrace(List<Node> path, Node goal) {
        System.out.println("Tracing path\n");
        Node current = goal;
        path.add(current);
        while (current.parent != null) {
            current = current.parent;
            path.add(current);
        }
    }

    /**
     * Überprüft, ob eine Liste von Knoten einen bestimmten Knoten enthält.
     *
     * @param a - eine Liste von Knoten.
     * @param b - ein Knoten.
     * @return true, wenn die Liste von Knoten den mitgegebenen Knoten enthält. Ansonsten false.
     */
    public static boolean contains(List<Node> a, Node b) {
        boolean contains = false;
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).isSamePuzzle(b.puzzle)) {
                contains = true;
                return contains;
            }
        }
        return contains;
    }

    /**
     * Gibt einen Lösungsweg in Textform aus.
     *
     * @param solution - eine Liste von Knoten, die einen Lösungsweg repräsentieren.
     */
    public static void printSolution(List<Node> solution) {
        if (solution.size() > 0) {
            for (int i = solution.size() - 1; i >= 0; i--) {
                System.out.print(solution.get(i).toString());
            }
            System.out.println("\n" + solution.size() + " Schritt(e)\n");
        } else {
            System.out.println("No path found");
        }
    }

    /**
     * Speichert einen Lösungsweg in eine .txt-Datei.
     *
     * @param solution  - eine Liste von Knoten, die einen Lösungsweg repräsentieren.
     * @param algorithm - der Name des verwendeten Algorithmus.
     */
    public static void logSolution(List<Node> solution, String algorithm) {
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(algorithm + ".txt"), "utf-8"));
            writer.write("Lösung mit " + algorithm + ":\n");
            if (solution.size() > 0) {
                for (int i = solution.size() - 1; i >= 0; i--) {
                    writer.append(solution.get(i).toString());
                }
                writer.append("\n" + solution.size() + " Schritt(e)\n");
            } else {
                writer.append("No path found");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

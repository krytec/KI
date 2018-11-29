import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AStarSolver {
    public static void main(String[] args) {

        int[] puzzle = {
                8,3,5,
                4,1,6,
                2,7,0
        };

        Node root = new Node(puzzle);

        List<Node> solutionH1 = suche(root, "h1");


        System.out.println("-- ASTARSEARCH --");
        printSolution(solutionH1);
        System.out.println();
        logSolution(solutionH1, "ASTARSEARCH");

        List<Node> solutionH2 = suche(root, "h2");
        System.out.println("-- ASTARSEARCH2 --");
        printSolution(solutionH2);
        System.out.println();
        logSolution(solutionH2, "ASTARSEARCH2");

        List<Node> solutionH3 = suche(root, "h3");
        System.out.println("-- ASTARSEARCH3 --");
        printSolution(solutionH3);
        System.out.println();
        logSolution(solutionH3, "ASTARSEARCH3");

    }
    public static List<Node> suche(Node root, String heuristic) {
        List<Node> pathToSolution = new ArrayList<>();
        List<Node> openList = new ArrayList<>();
        List<Node> closedList = new ArrayList<>();
        int g = 0;
        openList.add(root);

        //Solange noch knoten da sind suchen wir
        while (!openList.isEmpty()) {
            Node currentNode;
            if(openList.size()==1){
                currentNode = openList.get(0);
            }else{
                int curf;
                int lowestf = Integer.MAX_VALUE;
                currentNode = openList.get(0);
                for (Node node:openList){
                    curf = node.getH(heuristic)+g;
                    if(curf < lowestf){
                        currentNode = node;
                        lowestf = curf;

                    }
                }
            }
            openList.remove(currentNode);
            currentNode.expandNode();
            boolean lower;
            for(Node child: currentNode.children){
                lower = true;
                if (child.goalTest()) {
                    System.out.println("Goal found!");
                    pathTrace(pathToSolution, child);
                    return pathToSolution;
                }
                if(openList.size()!=0){
                    for(Node open: openList){
                        int openf = open.getH(heuristic)+g;
                        int successorf = child.getH(heuristic)+g;
                        if(openf < successorf){
                            lower = false;
                        }
                    }

                }else if(closedList.size()!=0){
                    for(Node close: closedList){
                        int closef = close.getH(heuristic)+g;
                        int successorf = child.getH(heuristic)+g;
                        if(closef < successorf){
                            lower = false;
                        }
                    }

                }
                if(lower){
                    openList.add(child);
                }
            }
            closedList.add(currentNode);
            g++;
        }
        return pathToSolution;
    }

    //rückwärts die parents durchgehen
    public static void pathTrace(List<Node> path, Node goal) {
        System.out.println("Tracing path\n");
        Node current = goal;
        path.add(current);
        while (current.parent != null) {
            current = current.parent;
            path.add(current);
        }
    }

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

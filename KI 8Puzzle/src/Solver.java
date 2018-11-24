import java.util.ArrayList;
import java.util.List;

public class Solver {

    public static void main(String[] args){
        int[] puzzle = {
                2,8,3,
                1,6,4,
                7,0,5
        };

        Node root = new Node(puzzle);
        List<Node> solution = Breitensuche(root);
        if(solution.size()>0){
            for(int i=0;i<solution.size();i++){
                solution.get(i).prinzPuzzle();
            }
        }else{
            System.out.println("No path found");
        }
    }

    /**
     * Simple Breitensuche mit hilfe einer einfachen queue
     * @param root
     * @return
     */
    public static List<Node> Breitensuche(Node root){
        List<Node> pathToSolution = new ArrayList<>();
        List<Node> openList = new ArrayList<>();
        List<Node> closedList = new ArrayList<>();

        openList.add(root);
        boolean reachedGoal = false;

        //Solange wir das goal nicht erreicht haben und noch nodes da sind suchen wir
        while(!openList.isEmpty() && !reachedGoal){
            //abarbeiten des obersten knotens
            Node currentNode = openList.get(0);
            closedList.add(currentNode);
            openList.remove(0);

            //alle richtungen gehen
            currentNode.expandNode();

            //alle childs abfangen
            for(int i =0;i < currentNode.children.size();i++){
                Node currentChild = currentNode.children.get(i);
                if(currentChild.GoalTest()){
                    System.out.println("Goal found!");
                    reachedGoal = true;
                    pathTrace(pathToSolution,currentChild);
                }
                //wenn der Goaltest nicht durchgeht wird hier das currentChild an die openList angehängt
                // Beispiel pathtracing : a
                //                       b,c,d
                //                      efg hij klm
                // erste queue a, dann bcd, dann cdefg dann defghij und so weiter
                if(!contains(openList,currentChild) && !contains(closedList,currentChild)){
                    openList.add(currentChild);
                }

            }

        }

        return pathToSolution;
    }

    //rückwärts die parents durchgehen
    public static void pathTrace(List<Node> path, Node goal){
        System.out.println("Tracing path");
        Node current = goal;
        current.prinzPuzzle();
        path.add(current);
        while(current.parent != null){
            current = current.parent;
            path.add(current);
            current.prinzPuzzle();
        }
    }

    public static boolean contains(List<Node> a,Node b){
        boolean contains = false;
        for(int i = 0; i < a.size(); i++){
            if(a.get(i).isSamePuzzle(b.puzzle)){
                contains = true;
            }
        }
        return contains;
    }
}

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class Node {

    public List<Node> children = new ArrayList<Node>();
    public Node parent;
    public int[] puzzle = new int[9];
    public int x = 0;
    private int col = 3;
    public Node(int[] p){
        puzzle = p;
    }

    public void MoveRight(int[]p,int i){
        if(i % col < col -1){
            int[] pc = new int[9];
            copyPuzzle(pc,p);
            int temp = pc[i+1];
            pc[i+1] = pc[i];
            pc[i] = temp;
            Node child = new Node(pc);
            children.add(child);
            child.parent = this;
        }
    }

    public void MoveLeft(int[]p,int i){
        if(i % col > 0){
            int[] pc = new int[9];
            copyPuzzle(pc,p);
            int temp = pc[i-1];
            pc[i-1] = pc[i];
            pc[i] = temp;
            Node child = new Node(pc);
            children.add(child);
            child.parent = this;
        }
    }

    public void MoveUp(int[]p,int i){
        if(i - col >= 0){
            int[] pc = new int[9];
            copyPuzzle(pc,p);
            int temp = pc[i-3];
            pc[i-3] = pc[i];
            pc[i] = temp;
            Node child = new Node(pc);
            children.add(child);
            child.parent = this;
        }
    }

    public void MoveDown(int[]p,int i){
        if(i + col  < puzzle.length){
            int[] pc = new int[9];
            copyPuzzle(pc,p);
            int temp = pc[i+3];
            pc[i+3] = pc[i];
            pc[i] = temp;
            Node child = new Node(pc);
            children.add(child);
            child.parent = this;
        }
    }

    public void expandNode(){
        for(int i = 0; i < puzzle.length; i++){
            if(puzzle[i] == 0){
                x=i;
            }
        }
        MoveDown(puzzle,x);
        MoveLeft(puzzle,x);
        MoveRight(puzzle,x);
        MoveUp(puzzle,x);
    }
    public void setPuzzle(int[] p){
        for (int i =0;i<puzzle.length;i++){
            this.puzzle[i] = p[i];
        }
    }

    public void copyPuzzle(int[]a,int[] b){
        for(int i=0;i<a.length;i++){
            a[i] = b[i];
        }
    }
    public boolean GoalTest() {
        boolean isGoal = true;
        int[] goal = {
                1,2,3,
                8,0,4,
                7,6,5
        };
        for(int i = 0;i<goal.length;i++){
            if(puzzle[i] != goal[i]){
                isGoal = false;
            }
        }
        return isGoal;
    }

    public boolean isSamePuzzle(int[] p){
        boolean samePuzzle = true;
        for(int i = 0; i < p.length; i++){
            if(puzzle[i] != p[i]){
                samePuzzle = false;
            }
        }
        return samePuzzle;
    }
    public void prinzPuzzle(){
        System.out.println();
        int m = 0;
        for(int i=0;i<col;i++){
            for(int j=0;j<col;j++){
                System.out.print(" "+this.puzzle[m]+" ");
                m++;
            }
            System.out.println("");
        }
    }
}

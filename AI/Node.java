package AI;
public class Node{
    Node parent;
    int gCost;
    int hCost;
    int fCost;
    int col;
    int row;
    boolean open;
    boolean solid;
    boolean checked;
    public Node(int col,int row){
        this.col = col;
        this.row = row;
    }
}
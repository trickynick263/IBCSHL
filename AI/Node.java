package AI;

public class Node {
    Node parent;
    public int col;
    public int row;
    public int gCost;
    public int hCost;
    public int fCost;
    boolean solid;
    boolean open;
    boolean checked;

    public Node(int col, int row){
        this.row = row;
        this.col = col;
    }
}

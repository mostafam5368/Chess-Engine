package piece;

public final class King extends Piece
{
    public King(String t, int r, int c){
        super(t, r, c);
        reach = 1;
        
        moveset = new int[][]{
            {0,-1},{1,0},{0,1},{-1,0},
            {1,-1},{1,1},{-1,1},{-1,-1}
        };
        
        //lineOfSight = new Path[moveset.length];
        buildPaths();
    }
    
    public String toString(){
        return "+";
    }
}

package piece;

public class Knight extends Piece
{
    public Knight(String t, int r, int c){
        super(t, r, c);
        maxTilesPerMove = 1;
        
        moveset = new int[][]{
            {1,-2},{1,2},{-1,2},{-1,-2},
            {2,-1},{2,1},{-2,1}, {-2,-1}
        };
        
        lineOfSight = new Path[moveset.length];
        buildPaths();
    }
    
    public String toString(){
        return "?";
    }   
}

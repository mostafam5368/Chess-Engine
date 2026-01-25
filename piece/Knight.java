package piece;

public final class Knight extends Piece
{
    public Knight(String t, int r, int c){
        super(t, r, c);
        reach = 1;
        
        moveset = new int[][]{
            {1,-2},{1,2},{-1,2},{-1,-2},
            {2,-1},{2,1},{-2,1}, {-2,-1}
        };
        
        paths = new Path[moveset.length];
        buildPaths();
    }
    
    public String toString(){
        return "?";
    }   
}

package piece;
import main.Chess;

public final class Bishop extends Piece
{
    public Bishop(String t, int r, int c){
        super(t, r, c);
        reach = Chess.INF_REACH;
        
        moveset = new int[][]{
            {1,-1},{1,1},{-1,1},{-1,-1}
        };
        
        paths = new Path[moveset.length];
        buildPaths();
    }
    
    public String toString(){
        return "%";
    }
}

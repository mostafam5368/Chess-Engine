package piece;
import main.Chess;

public class Bishop extends Piece
{
    public Bishop(String t, int r, int c){
        super(t, r, c);
        maxTilesPerMove = Chess.board.length - 1;
        
        moveset = new int[][]{
            {1,-1},{1,1},{-1,1},{-1,-1}
        };
        
        lineOfSight = new Path[moveset.length];
        buildPaths();
    }
    
    public String toString(){
        return "%";
    }
}

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
        
        for (int[] dir: moveset){
            lineOfSight.add(new Path(dir, maxTilesPerMove));
        }
    }
    
    public String toString(){
        return "%";
    }
    
}

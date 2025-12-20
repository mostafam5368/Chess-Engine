package piece;
import main.Chess;

public class Rook extends Piece
{
    public Rook(String t, int c, int r){
        super(t,c,r);
        maxTilesPerMove = Chess.board.length - 1;
        
        moveset = new int[][]{
            {0,-1},{1,0},{0,1},{-1,0}
        };
        
        for (int[] dir: moveset){
            lineOfSight.add(new Path(dir, maxTilesPerMove));
        }
    }
    
    public String toString(){
        return "#";
    }
    
}

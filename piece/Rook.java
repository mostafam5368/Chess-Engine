package piece;
import main.Chess;

public class Rook extends Piece
{
    public Rook(String t, int r, int c){
        super(t, r, c);
        maxTilesPerMove = Chess.board.length - 1;
        
        moveset = new int[][]{
            {0,-1},{1,0},{0,1},{-1,0}
        };
        
        lineOfSight = new Path[moveset.length];
        buildPaths();
    }
    
    public String toString(){
        return "#";
    } 
}

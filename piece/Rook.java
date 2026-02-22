package piece;
import main.Chess;

public final class Rook extends Piece
{
    public Rook(King k, int r, int c){
        super(k, r, c);
        reach = Chess.INF_REACH;
        
        moveset = new int[][]{
            {0,-1},{1,0},{0,1},{-1,0}
        };
        
        paths = new Path[moveset.length];
        buildPaths();
    }
    
    public String toString(){
        String str = "R";

        if (team.equals("black")){
            str = str.toLowerCase();
        }

        return str;
    } 
}

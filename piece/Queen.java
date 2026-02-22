package piece;
import main.Chess;

public final class Queen extends Piece
{
    public Queen(King k, int r, int c){
        super(k, r, c);
        reach = Chess.INF_REACH;
        
        moveset = new int[][]{
            {0,-1},{1,0},{0,1},{-1,0},
            {1,-1},{1,1},{-1,1},{-1,-1}
        };
        
        paths = new Path[moveset.length];
        buildPaths();
    }
    
    public String toString(){
        String str = "Q";

        if (team.equals("black")){
            str = str.toLowerCase();
        }

        return str;
    }
    
}

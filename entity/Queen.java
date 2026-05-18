package entity;
import game.Chess;

public final class Queen extends Piece
{
    public Queen(King k, int r, int c){
        super(k, r, c);
        materialValue = 9;
        reach = INF_REACH;
        
        moveset = new int[][]{
            {0,-1},{1,0},{0,1},{-1,0},
            {1,-1},{1,1},{-1,1},{-1,-1}
        };
    }
    
    public String toString(){
        String str = "Q";
        if (team.equals(Chess.black.team)) str = str.toLowerCase();
        return str;
    }
}

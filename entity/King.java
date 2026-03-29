package entity;
import game.Chess;

public final class King extends Piece
{
    public King(String t, int r, int c){
        super(t, r, c);
        material = 0;
        reach = 1;
        
        moveset = new int[][]{
            {0,-1},{1,0},{0,1},{-1,0},
            {1,-1},{1,1},{-1,1},{-1,-1}
        };
    }

    @Override
    public boolean inCheck(){
        return isCapturable();
    }

    @Override
    public void place(){
        super.place();
        // Chess.board[row][col - 2].seenBy.put(this, true);
        // Chess.board[row][col + 2].seenBy.put(this, true);
    }
    
    public String toString(){
        String str = "K";
        if (team.equals("black")) str = str.toLowerCase();

        return str;
    }
}

package entity;

import game.Chess;

public final class Rook extends Piece
{
    protected boolean hasMoved;
    private int kingCastle;
    

    public Rook(King k, int r, int c){
        super(k, r, c);
        materialValue = 5;
        reach = INF_REACH;

        hasMoved = false;

        int rightOrLeft = -(king.col - col) / Math.abs(king.col - col);
        kingCastle = king.col + rightOrLeft * 2;
        
        moveset = new int[][]{
            {0,-1},{1,0},{0,1},{-1,0}
        };
    }

    @Override
    public boolean move(int x, int y){
        boolean completed = super.move(x, y);

        if (completed){
            if (!hasMoved){
                Chess.board[king.row][kingCastle].seenBy.replace(king, false);
                hasMoved = true;
            }
        }

        return completed;
    }

    @Override
    public void removeFromBoard(){
        super.removeFromBoard();
        Chess.board[king.row][kingCastle].seenBy.replace(king, false);
    }
    
    public String toString(){
        String str = "R";
        if (team.equals("black")) str = str.toLowerCase();
        return str;
    } 
}

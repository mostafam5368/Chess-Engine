package entity;

import game.Chess;

public final class King extends Piece
{
    public King(String t, int r, int c){
        super(t, r, c);
        materialValue = 0;
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
        capture(Chess.board[row][col]);

        for (int[] dir: moveset){
            if (dir[1] == 0){
                new Path(new int[]{dir[0] * 2, 0}, 1, Tile.class);
                new Path(dir, reach);
            }
            else {
                new Path(dir, reach);
            }
        }
    }

    @Override
    public boolean move(int x, int y){
        if (Math.abs(col - y) == 2){
            Rook castlingRook;

            if (y < Chess.board[row].length / 2){
                castlingRook = (Rook) Chess.board[row][0];
            }
            else {
                castlingRook = (Rook) Chess.board[row][Chess.board.length - 1];
            }

            if (legalCastle(castlingRook)){
                boolean completed = super.move(x, y);
                if (completed) castle(castlingRook);
                return completed;
            }
            else {
                return false;
            }
        }

        return super.move(x, y);
    }

    public boolean legalCastle(Rook r){
        int rightOrLeft = -(col - r.col) / Math.abs(col - r.col);

        for (int i = col + rightOrLeft; i > 1 && i < Chess.board[row].length - 1; i += rightOrLeft){
            // fix opponent
            if (Chess.board[row][i].capturableBy("black", Piece.class).size() > 0){
                return false;
            }
        }

        for (int i = col + rightOrLeft; i > 0 && i < Chess.board[row].length - 1; i += rightOrLeft){
            if (Chess.board[row][i].isOccupied()){
                return false;
            }
        }

        return !inCheck() && !r.isCapturable() && !r.hasMoved;
    }

    public void castle(Rook r){
        if (r.col < col){
            r.move(row, col + 1);
        }
        else if (r.col > col){
            r.move(row, col - 1);
        }
    }
    
    public String toString(){
        String str = "K";
        if (team.equals("black")) str = str.toLowerCase();
        if (inCheck()) str = "!";
        return str;
    }
}

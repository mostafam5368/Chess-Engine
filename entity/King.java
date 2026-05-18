package entity;
import java.util.ArrayList;
import game.Chess;

public final class King extends Piece
{
    public int materialGained;

    public King(String t, int r, int c){
        super(t, r, c);
        materialValue = 0;
        reach = 1;
        materialGained = 0;
        
        moveset = new int[][]{
            {0,-1},{1,0},{0,1},{-1,0},
            {1,-1},{1,1},{-1,1},{-1,-1}
        };
    }

    @Override
    public void place(){
        capture(Chess.board[row][col]);

        for (int[] dir: moveset){
            if (dir[1] == 0){
                Path castlingPath = new Path(new int[]{dir[0] * 2, 0}, 1, Tile.class);
                castlingPath.build();

                Path sidePath = new Path(dir, reach);
                sidePath.build();
            }
            else {
                Path path = new Path(dir, reach);
                path.build();
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

        for (int i = col + rightOrLeft; i >= col - 2 && i < col + 2; i += rightOrLeft){
            if (Chess.board[row][i].capturableBy(Chess.opponents.get(this).team).size() > 0){
                return false;
            }
        }

        for (int i = col + rightOrLeft; i > 0 && i < Chess.board[row].length - 1; i += rightOrLeft){
            if (Chess.board[row][i].isOccupied()){
                return false;
            }
        }

        return !inCheck() && !r.isCapturable();
    }

    public void castle(Rook r){
        if (r.col < col){
            r.move(row, col + 1);
        }
        else if (r.col > col){
            r.move(row, col - 1);
        }
    }

    @Override
    public boolean inCheck(){
        return isCapturable();
    }

    public boolean inCheckmate(){
        if (!inCheck()) return false;

        int originalRow = row;
        int originalCol = col;
        boolean canMove = false;

        // try to move in every direction
        for (int[] dir: moveset){
            int x = row + dir[1];
            int y = col + dir[0];

            if (Chess.legalBounds(x, y)){
                Entity target = Chess.board[x][y];

                if (target.seenBy.get(this)){
                    boolean tryMove = move(x, y);

                    if (tryMove){
                        canMove = true;

                        target.place();
                        capture(Chess.board[originalRow][originalCol]);
                        buildPaths();

                        break;
                    }
                }
            }
        }

        if (canMove){
            return false;
        }

        ArrayList<Piece> checkingPieces = capturableBy(Chess.opponents.get(this).team);

        // if it's a single checking piece that can be captured, not checkmate
        if (checkingPieces.size() == 1){
            Piece checkingPiece = checkingPieces.get(0);
            ArrayList<Piece> canCapture = checkingPiece.capturableBy(team);

            if (canCapture.size() > 0){
                if (!(canCapture.size() == 1 && canCapture.contains(this))){
                    return false;
                }
            }

            Path checkingPath = checkingPiece.seenEntities.get(this);

            for (int i = 0; i < checkingPath.contents.size() - 1; i++){
                Entity entity = checkingPath.contents.get(i);
                ArrayList<Piece> canBlock = entity.capturableBy(team);

                if (canBlock.size() > 0){
                    if (!(canBlock.size() == 1 && canBlock.contains(this))){
                        return false;
                    }
                }
            }
        }

        return true;
    }
    
    public String toString(){
        String str = "K";
        if (team.equals(Chess.black.team)) str = str.toLowerCase();
        if (inCheck()) str = "!";
        return str;
    }
}

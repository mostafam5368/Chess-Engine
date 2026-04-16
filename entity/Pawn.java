package entity;

import game.Chess;

import java.util.HashMap;

public final class Pawn extends Piece
{
    private int forward;
    private int promotionRow;

    public Pawn(King k, int r, int c){
        super(k, r, c);
        materialValue = 1;

        reach = 1;
        if (team.equals("white")) forward = -1;
        else forward = 1;
        
        moveset = new int[][]{
            {0, forward},{-1, forward},{1, forward}
        };

        if (forward < 1) promotionRow = 0;
        else promotionRow = Chess.board.length - 1;
    }

    @Override
    public void place(){
        capture(Chess.board[row][col]);

        for (int[] dir: moveset){
            if (dir[0] == 0) {
                new Path(dir, 2, Tile.class);
            }
            else {
                new Path(dir, reach, Piece.class);
            }
        }
    }

    @Override
    public void buildPaths(){
        seenEntities = new HashMap<>();

        for (int[] dir: moveset){
            if (dir[0] == 0){
                new Path(dir, reach, Tile.class);
            }
            else {
                new Path(dir, reach, Piece.class);
            }
        }
    }
    
    @Override
    public boolean move(int x, int y){
        boolean completed = super.move(x, y);

        if (row == promotionRow){
            promote();
        }

        return completed;
    }

    private void promote(){
        System.out.println("Promote to:");
        System.out.println("1. Q\t2. R");
        System.out.println("3. N\t4. B");

        int promotion = Chess.reader.nextInt();
        while (promotion < 1 || promotion > 4){
            promotion = Chess.reader.nextInt();
        }

        switch (promotion){
            case 1:
                new Queen(king, row, col).place();
                break;
            case 2:
                new Rook(king, row, col).place();
                break;
            case 3:
                new Knight(king, row, col).place();
                break;
            case 4:
                new Bishop(king, row, col).place();
                break;
            default: break; 
        }
    }
    
    public String toString(){
        String str = "P";
        if (team.equals("black")) str = str.toLowerCase();
        return str;
    }
}

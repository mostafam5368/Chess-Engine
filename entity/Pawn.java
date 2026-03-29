package entity;

import game.Chess;

import java.util.HashMap;

public final class Pawn extends Piece
{
    private int forward;
    private int startingRow;

    public Pawn(King k, int r, int c){
        super(k, r, c);
        material = 1;

        reach = 2;
        if (team.equals("white")) forward = -1;
        else forward = 1;
        
        moveset = new int[][]{
            {0, forward},{-1, forward},{1, forward}
        };

        startingRow = r;
    }

    @Override
    public void buildPaths(){
        seenEntities = new HashMap<>();
        new Path(moveset[0], reach, Tile.class);
        new Path(moveset[1], 1, Piece.class);
        new Path(moveset[2], 1, Piece.class);
    }
    
    @Override
    public boolean move(int x, int y){
        boolean output = super.move(x, y);

        if (output){
            if (reach > 1){
                reach = 1;
                blind();
                buildPaths();
            }

            if (row == Chess.board.length + (startingRow * forward) - 2){
                // 9 - 6 - 2
                promote();
            }
        }

        return output;
    }

    private void promote(){
        System.out.println("Promote to:");
        System.out.println("1. Queen\t2. Rook");
        System.out.println("3. Knight\t4. Bishop");

        int promotion = Chess.reader.nextInt();
        while (promotion < 1 && promotion > 4){
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

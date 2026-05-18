package entity;
import java.util.ArrayList;
import game.Chess;

public final class Pawn extends Piece
{
    private int forward;
    private int promotionRow;

    public Pawn(King k, int r, int c){
        super(k, r, c);
        materialValue = 1;

        reach = 1;
        if (team.equals(Chess.white.team)) forward = -1;
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
                Path movingPath = new Path(dir, 2, Tile.class);
                movingPath.build();
            }
            else {
                Path capturingPath = new Path(dir, reach, Piece.class);
                capturingPath.build();
            }
        }
    }

    @Override
    public void buildPaths(){
        for (int[] dir: moveset){
            if (dir[0] == 0){
                Path movingPath = new Path(dir, reach, Tile.class);
                movingPath.build();
            }
            else {
                Path capturingPath = new Path(dir, reach, Piece.class);
                capturingPath.build();
            }
        }
    }
    
    @Override
    public boolean move(int x, int y){
        int startingRow = row;
        boolean completed = super.move(x, y);

        if (completed){
            if (Math.abs(startingRow - x) == 2){
                ArrayList<Pawn> enPassant = new ArrayList<>();
                
                if (col > 0){
                    Entity left = Chess.board[row][col - 1];

                    if (left instanceof Pawn && !isAlly(left)){
                        enPassant.add((Pawn) left);
                    }
                }
                
                if (col < Chess.board[row].length - 1){
                    Entity right = Chess.board[row][col + 1];

                    if (right instanceof Pawn && !isAlly(right)){
                        enPassant.add((Pawn) right);
                    }
                }

                if (enPassant.size() > 0){
                    int rowBehind = row - forward;

                    // grant move access
                    for (Pawn pawn: enPassant){
                        Chess.board[rowBehind][col].seenBy.put(pawn, true);
                    }

                    // prompt opponent
                    if (!Chess.opponents.get(king).inCheckmate()){
                        Chess.playRound(Chess.opponents.get(king));

                        if (enPassant.contains(Chess.board[rowBehind][col])){
                            // "capture" pawn
                            new Tile(row, col).place();
                            Chess.opponents.get(king).materialGained += materialValue;
                        }
                        else {
                            // remove move access
                            for (Pawn pawn: enPassant){
                                Chess.board[rowBehind][col].seenBy.remove(pawn);
                            }
                        }

                        // complete round of prompting
                        if (!king.inCheckmate()){
                            Chess.playRound(king);
                        }
                    }
                }
            }

            if (row == promotionRow){
                promote();
            }
        }
        
        return completed;
    }

    private void promote(){
        System.out.println("Promote:");
        System.out.println("1. Queen\t2. Rook");
        System.out.println("3. Knight\t4. Bishop");

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
        if (team.equals(Chess.black.team)) str = str.toLowerCase();
        return str;
    }
}

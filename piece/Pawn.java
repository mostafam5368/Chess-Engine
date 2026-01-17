package piece;

public final class Pawn extends Piece
{
    private int forward;

    public Pawn(String t, int r, int c){
        super(t, r, c);

        reach = 2;
        forward = -1;
        
        moveset = new int[][]{
            {0, forward},{-1, forward},{1, forward}
        };
        
        buildPaths();
    }

    @Override
    public void buildPaths(){
        Path p1 = new Path(moveset[0], reach, Tile.class);
        Path p2 = new Path(moveset[1], 1, Piece.class);
        Path p3 = new Path(moveset[2], 1, Piece.class);
    }
    
    @Override
    public void move(int x, int y){
        super.move(x, y);
        
        // if (reach > 1){
        //     reach = 1;
        //     lineOfSight.get(Chess.board[x][y]).updateMax(1);
        // }
    }
    
    public String toString(){
        return "^";
    }
}

package piece;

import java.util.HashMap;

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
        
        paths = new Path[moveset.length];
        buildPaths();
    }

    @Override
    public void buildPaths(){
        foundEntities = new HashMap<>();
        
        paths[0] = new Path(moveset[0], reach, Tile.class);
        paths[1] = new Path(moveset[1], 1, Piece.class);
        paths[2] = new Path(moveset[2], 1, Piece.class);
    }
    
    @Override
    public void move(int x, int y){
        super.move(x, y);
        
        if (reach > 1){
            reach = 1;
            buildPaths();
        }
    }
    
    public String toString(){
        return "^";
    }
}

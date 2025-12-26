package piece;

public class King extends Piece
{
    public King(String t, int r, int c){
        super(t, r, c);
        maxTilesPerMove = 1;
        
        moveset = new int[][]{
            {0,-1},{1,0},{0,1},{-1,0},
            {1,-1},{1,1},{-1,1},{-1,-1}
        };
        
        for (int[] dir: moveset){
            lineOfSight.add(new Path(dir, maxTilesPerMove));
        }
    }
    
    public String toString(){
        return "+";
    }
}

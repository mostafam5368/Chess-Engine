package piece;
public class King extends Piece
{
    public King(String t, int c, int r){
        super(t,c,r);
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

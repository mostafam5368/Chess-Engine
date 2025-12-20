package piece;
public class Knight extends Piece
{
    public Knight(String t, int c, int r){
        super(t,c,r);
        maxTilesPerMove = 1;
        
        moveset = new int[][]{
            {1,-2},{1,2},{-1,2},{-1,-2},
            {2,-1},{2,1},{-2,1}, {-2,-1}
        };
        
        for (int[] dir: moveset){
            lineOfSight.add(new Path(dir, maxTilesPerMove));
        }
    }
    
    public String toString(){
        return "?";
    }
    
}

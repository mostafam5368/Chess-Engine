
public class Bishop extends Piece
{
    public Bishop(String t, int c, int r){
        super(t,c,r);
        maxTilesPerMove = Chess.board.length - 1;
        
        moveset = new int[][]{
            {1,-1},{1,1},{-1,1},{-1,-1}
        };
        
        for (int[] dir: moveset){
            lineOfSight.add(new Path(dir, maxTilesPerMove));
        }
    }
    
    public String toString(){
        return "%";
    }
    
}

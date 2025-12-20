
public class Pawn extends Piece
{
    private int maxTilesPerCapture;
    private int[][] captureset;
    
    public Pawn(String t, int c, int r){
        super(t,c,r);
        maxTilesPerMove = 2;
        maxTilesPerCapture = 1;
        
        int forward = -1;
        if (team.equals("black")){
            forward = 1;
        }
        
        moveset = new int[][]{
            {0, forward}
        };
        
        captureset = new int[][]{
            {-1, forward},{1, forward}
        };
        
        for (int[] dir: moveset){
            lineOfSight.add(new Path(dir, maxTilesPerMove));
        }
        
        for (int[] dir: captureset){
            lineOfSight.add(new Path(dir, maxTilesPerCapture));
        }
    }
    
    @Override
    public void move(int x, int y){
        super.move(x, y);
        
        if (maxTilesPerMove > 1){
            maxTilesPerMove = 1;
            lineOfSight.get(0).updateMax(maxTilesPerMove);
        }
    }
    
    public String toString(){
        return "^";
    }
}

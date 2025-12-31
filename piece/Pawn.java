package piece;

public class Pawn extends Piece
{
    private int maxTilesPerCapture;
    private int[][] captureset;
    
    public Pawn(String t, int r, int c){
        super(t, r, c);
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
        
        lineOfSight = new Path[moveset.length + captureset.length];
        buildPaths();
    }
    
    @Override
    public void buildPaths(){
        super.buildPaths();

        for (int i = moveset.length - 1; i < captureset.length; i++){
            int[] dir = captureset[i];
            lineOfSight[i] = new Path(dir, maxTilesPerCapture);
        }
    }
    
    @Override
    public void move(int x, int y){
        super.move(x, y);
        
        if (maxTilesPerMove > 1){
            maxTilesPerMove = 1;
            lineOfSight[0].updateMax(maxTilesPerMove);
        }
    }
    
    public String toString(){
        return "^";
    }
}

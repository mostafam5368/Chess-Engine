package piece;

public class Pawn extends Piece
{
    private int maxTilesPerCapture, forward;
    private int[][] captureset;
    
    public Pawn(String t, int r, int c){
        super(t, r, c);

        maxTilesPerMove = 2;
        maxTilesPerCapture = 1;
        
        forward = -1;
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

        lineOfSight[1] = new Path(captureset[0], maxTilesPerCapture);
        lineOfSight[2] = new Path(captureset[1], maxTilesPerCapture);
    }
    
    @Override
    public void move(int x, int y){
        super.move(x, y);
        
        if (maxTilesPerMove > 1){
            lineOfSight[0].updateMax(1);
        }
    }
    
    public String toString(){
        return "^";
    }
}

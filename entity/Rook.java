package entity;

public final class Rook extends Piece
{
    public Rook(King k, int r, int c){
        super(k, r, c);
        material = 5;
        reach = INF_REACH;
        
        moveset = new int[][]{
            {0,-1},{1,0},{0,1},{-1,0}
        };
    }
    
    public String toString(){
        String str = "R";
        if (team.equals("black")) str = str.toLowerCase();

        return str;
    } 
}

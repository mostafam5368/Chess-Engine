package entity;

public final class Bishop extends Piece
{
    public Bishop(King k, int r, int c){
        super(k, r, c);
        materialValue = 3;
        reach = INF_REACH;
        
        moveset = new int[][]{
            {1,-1},{1,1},{-1,1},{-1,-1}
        };
    }
    
    public String toString(){
        String str = "B";
        if (team.equals("black")) str = str.toLowerCase();
        return str;
    }
}

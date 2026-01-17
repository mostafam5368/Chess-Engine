package piece;
import main.Chess;

import java.util.Map;
import java.util.HashMap;

public abstract class Entity
{
    //Instance Variables
    protected String team;
    protected int row, col;
    public Map<Piece, Boolean> seenBy;


    //Constructors
    public Entity(String t, int r, int c){
        team = t;
        row = r;
        col = c;

        seenBy = new HashMap<>();
    }


    //Boolean Methods
    public boolean validBounds(int x, int y){
        return (x < Chess.board.length && x >= 0) && (y < Chess.board[0].length && y >= 0);
    }

    public boolean isOccupied(){
        return this instanceof Piece;
    }

    public boolean isAlly(Entity target){
        return team.equals(target.team);
    }

    public boolean isSeen(){
        return seenBy.containsValue(true);
    }
    
    //Void Methods
    public void remove(){
        notifySeenBy();
        seenBy.clear();
    }

    public void notifySeenBy(){
        Map<Piece, Boolean> copy = new HashMap<>(seenBy);

        for (Piece piece: copy.keySet()){
            piece.lineOfSight.get(this).update();
        }
    }
}

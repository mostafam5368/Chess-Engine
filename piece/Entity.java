package piece;
import main.Chess;

import java.util.HashSet;
import java.util.Set;

public abstract class Entity
{
    //Instance Variables
    protected String team;
    protected int row, col;
    public Set<Piece> seenBy;


    //Constructors
    public Entity(String t, int r, int c){
        team = t;
        row = r;
        col = c;

        seenBy = new HashSet<>();
    }


    //Boolean Methods
    public boolean legalBounds(int x, int y){
        return (x < Chess.board.length && x >= 0) && (y < Chess.board[0].length && y >= 0);
    }

    public boolean isOccupied(){
        return this instanceof Piece;
    }

    public boolean isAlly(Entity target){
        return team.equals(target.team);
    }

    public boolean isSeen(){
        return !seenBy.isEmpty();
    }

    
    //Void Methods
    public void remove(){
        updateSeenBy();
        seenBy.clear();
    }

    public void updateSeenBy(){
        for (Piece piece: seenBy){
            piece.updateLineOfSight();
        }
    }
}

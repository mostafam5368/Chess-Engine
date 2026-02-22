package piece;
import main.Chess;

import java.util.HashMap;
import java.util.HashSet;

// This class represents a Tile or Piece on the board.
public abstract class Entity
{
    // Instance Variables
    protected String team;
    protected int row, col;
    public HashMap<Piece, Boolean> seenBy;


    // Constructors
    public Entity(String t, int r, int c){
        team = t;
        row = r;
        col = c;
        seenBy = new HashMap<>();
    }


    // Boolean Methods
    public boolean legalBounds(int x, int y){
        return (x < Chess.board.length && x >= 0) && (y < Chess.board[0].length && y >= 0);
    }

    public boolean isOccupied(){
        return this instanceof Piece;
    }

    public boolean isAlly(Entity target){
        return team.equals(target.team);
    }

    public boolean isCapturable(){
        return seenBy.containsValue(true);
    }
    
    /*
        The purpose of this method is to remove an Entity from the board in the case of a move or capture.
        All Pieces that previously saw the Entity are notified.
    */
    public void removeFromBoard(){
        notifyBoard();
        seenBy.clear();
    }

    /*
        The purpose of this method is to notify Pieces that see the Entity in the case of a move or capture.
        The Paths in which the Entity was previously stored are refreshed.
    */
    public void notifyBoard(){
        HashSet<Piece> copy = new HashSet<>(seenBy.keySet());

        for (Piece piece: copy){
            piece.seenEntities.get(this).refreshAt(this);
        }
    }
}

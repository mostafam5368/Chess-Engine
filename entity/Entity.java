package entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import game.Chess;

// This class represents a Tile or Piece on the board.
public abstract class Entity
{
    // Instance Variables
    public String team;
    public int row, col;
    protected HashMap<Piece, Boolean> seenBy;
    public int materialValue;


    // Constructors
    public Entity(String t, int r, int c){
        team = t;
        row = r;
        col = c;
        seenBy = new HashMap<>();
    }


    // Boolean Methods
    public boolean isOccupied(){
        return this instanceof Piece;
    }
    public boolean isAlly(Entity target){
        return team.equals(target.team);
    }
    public boolean isCapturable(){
        return seenBy.containsValue(true);
    }
    
    public boolean onCol(int c){
        return col == c;
    }
    public boolean onRow(int r){
        return row == r;
    }

    /*
        The purpose of this method is to put the Entity on the board in place of the target Entity.
        Pieces that previously saw the target Entity are notified.
    */
    public void capture(Entity target){
        row = target.row;
        col = target.col;
        Chess.board[row][col] = this;
        target.removeFromBoard();
    }

    // The purpose of this method is to register the Entity on the board in its assigned location.
    public void place(){
        capture(Chess.board[row][col]);
    }
    
    /*
        The purpose of this method is to remove an Entity from the board in the case of a move or capture.
        Pieces that previously saw the Entity are notified.
    */
    public void removeFromBoard(){
        notifyPieces();
        seenBy.clear();
    }

    // The purpose of this method is to refresh the paths in which this Entity was previously seen in the case of a move or capture.
    public void notifyPieces(){
        HashSet<Piece> copy = new HashSet<>(seenBy.keySet());
        
        for (Piece piece: copy){
            piece.seenEntities.get(this).refreshAt(this);
        }
    }

    public ArrayList<Piece> capturableBy(String t){
        return capturableBy(t, Piece.class);
    }

    // The purpose of this method is to collect the Pieces of the specified team and type that can capture this Entity.
    public ArrayList<Piece> capturableBy(String t, Class<? extends Piece> type){
        ArrayList<Piece> output = new ArrayList<>();

        for (Piece piece: seenBy.keySet()){
            if (seenBy.get(piece)){
                if (type.isInstance(piece) && piece.team.equals(t)){
                    output.add(piece);

                    if (type == King.class) break;
                }
            }
        }

        return output;
    }
}

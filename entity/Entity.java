package entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import game.Chess;

// This class represents a Tile or Piece on the board.
public abstract class Entity
{
    // Instance Variables
    public String team;
    public int row, col;
    public HashMap<Piece, Boolean> seenBy;
    protected int material;


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
        if (c == '.') return true;
        return col == c;
    }
    public boolean onRow(int r){
        if (r == '.') return true;
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
        notifyBoard();
        seenBy.clear();
    }

    // The purpose of this method is to refresh the paths in which this Entity was previously seen in the case of a move or capture.
    public void notifyBoard(){
        HashSet<Piece> copy = new HashSet<>(seenBy.keySet());
        
        for (Piece piece: copy){
            piece.seenEntities.get(this).refreshAt(this);
        }
    }


    // The purpose of this method is to collect the Pieces of the specified team and type that can capture this Entity.
    public ArrayList<Piece> capturableBy(String t, Class<? extends Piece> type){
        ArrayList<Piece> output = new ArrayList<>();

        for (Entry<Piece, Boolean> entry: seenBy.entrySet()){
            if (entry.getValue()){
                if (type.isInstance(entry.getKey()) && entry.getKey().team.equals(t)){
                    output.add(entry.getKey());
                }
            }
        }

        return output;
    }
}

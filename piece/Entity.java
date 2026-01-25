package piece;
import main.Chess;

import java.util.HashMap;

public abstract class Entity
{
    //Instance Variables
    protected String team;
    protected int row, col;
    public HashMap<Piece, Boolean> foundBy;


    //Constructors
    public Entity(String t, int r, int c){
        team = t;
        row = r;
        col = c;
        foundBy = new HashMap<>();
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
        return foundBy.containsValue(true);
    }
    
    //Void Methods
    public void removeFromBoard(){
        notifyBoard();
        foundBy.clear();
    }

    public void notifyBoard(){
        HashMap<Piece, Boolean> copy = new HashMap<>(foundBy);

        for (Piece piece: copy.keySet()){
            piece.foundEntities.get(this).refreshAt(this);
        }
    }
}

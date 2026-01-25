package piece;
import main.Chess;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Piece extends Entity
{
    //Instance Variables
    protected int reach;
    protected int[][] moveset;
    public Path[] paths;
    public HashMap<Entity, Path> foundEntities;
    

    //Constructors
    public Piece(String t, int r, int c){
        super(t, r, c);
        capture(Chess.board[r][c]);
    }
    
    
    public class Path {
        //Variables
        private int[] direction;
        private int maxSize;
        private Class<? extends Entity> captureRule;
        public ArrayList<Entity> contents;


        //Constructors
        public Path(int[] dir, int m){
            this(dir, m, Entity.class);
        }

        public Path(int[] dir, int m, Class<? extends Entity> cr){
            direction = dir;
            maxSize = m;
            captureRule = cr;
            contents = new ArrayList<>();

            build(row + dir[1], col + dir[0]);
        }
        

        //Path Building
        public void build(int x, int y){
            if (!legalBounds(x, y)){
                return;
            }

            Entity current = Chess.board[x][y];
            buildTo(current);
            
            if (current.isOccupied() || contents.size() >= maxSize){
                return;
            }
            
            build(x + direction[1], y + direction[0]);
        }

        public void buildTo(Entity target){
            contents.add(target);
            foundEntities.put(target, this);
            target.foundBy.put(Piece.this, legalityOf(target));
        }

        public boolean legalityOf(Entity target){
            return canCapture(target) && captureRule.isInstance(target);
        }


        //Path Updating
        public void refreshAt(Entity e){
            Entity boardState = Chess.board[e.row][e.col];
            int entityIndex = contents.indexOf(e);

            contents.set(entityIndex, boardState);

            foundEntities.remove(e);
            foundEntities.put(boardState, this);

            updateVisibility(entityIndex);
        }

        public void clearVisibility(){
            for (Entity e: contents){
                e.foundBy.remove(Piece.this);
            }
        }

        public void updateVisibility(int index){
            for (int i = index; i < contents.size(); i++){
                Entity current = contents.get(i);
                current.foundBy.put(Piece.this, legalityOf(current));
            }
        }
        
        public void updateMax(int m){
            maxSize = m;
            updateVisibility(0);
        }
    }
    

    //Path Methods
    public void buildPaths(){
        foundEntities = new HashMap<>();

        for (int i = 0; i < moveset.length; i++){
            paths[i] = new Path(moveset[i], reach);
        }
    }

    public void blind(){
        for (Path p: paths){
            p.clearVisibility();
        }
    }


    //Move Validation Methods
    public boolean canCapture(Entity target){
        return !isAlly(target);
    }

    public boolean legalMove(int x, int y){
        if (!legalBounds(x, y)){
            return false;
        }

        return Chess.board[x][y].foundBy.getOrDefault(this, false);
    }
    

    //Movement Methods
    @Override
    public void removeFromBoard(){
        super.removeFromBoard();
        blind();
    }
    
    public void capture(Entity target){
        row = target.row;
        col = target.col;

        Chess.board[row][col] = this;
        target.removeFromBoard();
    }
    
    public void move(int x, int y){
        Chess.board[row][col] = new Tile(row, col);
        removeFromBoard();

        capture(Chess.board[x][y]);
        buildPaths();
    }
}

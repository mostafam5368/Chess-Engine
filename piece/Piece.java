package piece;
import main.Chess;

import java.util.Arrays;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.HashMap;

public abstract class Piece extends Entity
{
    //Instance Variables
    protected int reach;
    protected int[][] moveset;
    public Map<Entity, Path> lineOfSight;
    

    //Constructors
    public Piece(String t, int r, int c){
        super(t, r, c);
        lineOfSight = new HashMap<>();
        
        capture(Chess.board[r][c]);
    }
    
    
    public class Path {
        private int[] direction;
        private int max;
        private Class<? extends Entity> rule;
        private Set<Entity> entities;

        public Path(int[] dir, int m){
            this(dir, m, Entity.class);
        }

        public Path(int[] dir, int m, Class<? extends Entity> r){
            direction = dir;
            max = m;
            rule = r;
            entities = new LinkedHashSet<>();
            
            build(row + direction[1], col + direction[0]);
        }

        public void discover(Entity target){
            entities.add(target);
            lineOfSight.put(target, this);

            target.seenBy.put(Piece.this, legality(target) && rule.isInstance(target));
        }
        
        public void build(int x, int y){
            if (!validBounds(x, y)){
                return;
            }

            Entity current = Chess.board[x][y];
            discover(current);
            
            if (current.isOccupied() || entities.size() >= max){
                return;
            }
            
            build(x + direction[1], y + direction[0]);
        }

        public void cleanse(){
            for (Entity e: entities){
                e.seenBy.remove(Piece.this);
            }
        }
        
        public void rebuild(){
            for (Entity e: entities){
                lineOfSight.remove(e);
            }

            entities.clear();
            build(row + direction[1], col + direction[0]);
        }

        public void update(){
            cleanse();
            rebuild();
        }
        
        public void updateMax(int m){
            max = m;
            update();
        }

        public String toString(){
            return Arrays.toString(direction);
        }
    }
    

    //Path Methods
    public void buildPaths(){
        lineOfSight.clear();

        for (int[] dir: moveset){
            Path path = new Path(dir, reach);
        }
    }

    public void cleansePaths(){
        for (Path p: lineOfSight.values()){
            p.cleanse();
        }
    }


    //Move Validation Methods
    public boolean legality(Entity target){
        return !isAlly(target);
    }

    public boolean validMove(int x, int y){
        if (!validBounds(x, y)){
            return false;
        }

        return Chess.board[x][y].seenBy.getOrDefault(this, false);
    }
    

    //Movement Methods
    @Override
    public void remove(){
        super.remove();
        cleansePaths();
    }
    
    public void capture(Entity target){
        row = target.row;
        col = target.col;

        Chess.board[row][col] = this;
        target.remove();
    }
    
    public void move(int x, int y){
        Chess.board[row][col] = new Tile(row, col);
        remove();

        capture(Chess.board[x][y]);
        buildPaths();
    }
}

package piece;
import main.Chess;

import java.util.Map;
import java.util.HashMap;

public abstract class Piece extends Entity
{
    //Instance Variables
    protected int reach;
    protected int[][] moveset;
    protected Path[] lineOfSight;
    

    //Constructors
    public Piece(String t, int r, int c){
        super(t, r, c);
        capture(Chess.board[r][c]);
    }
    
    
    protected class Path {
        private int[] direction;
        private int max;
        private Class<? extends Entity> rule;
        private Map<Entity, Boolean> entities;

        public Path(int[] dir, int m){
            this(dir, m, Entity.class);
        }

        public Path(int[] dir, int m, Class<? extends Entity> r){
            direction = dir;
            max = m;
            rule = r;
            entities = new HashMap<>();
            
            build(row + direction[1], col + direction[0]);
        }

        public void add(Entity target){
            boolean val = legality(target) && rule.isInstance(target);

            entities.put(target, val);
            target.seenBy.put(Piece.this, val);
        }
        
        public void build(int x, int y){
            if (!validBounds(x, y)){
                return;
            }

            Entity current = Chess.board[x][y];
            add(current);
            
            if (current.isOccupied() || entities.size() >= max){
                return;
            }
            
            build(x + direction[1], y + direction[0]);
        }

        public void cleanse(){
            for (Entity e: entities.keySet()){
                e.seenBy.remove(Piece.this);
            }
        }
        
        public void rebuild(){
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
    }
    

    //Path Methods
    public Path inLineOfSight(Entity target){
        for (Path p: lineOfSight){
            if (p.entities.containsKey(target)){
                return p;
            }
        }
        
        return null;
    }

    public void buildPaths(){
        for (int i = 0; i < moveset.length; i++){
            int[] dir = moveset[i];
            lineOfSight[i] = new Path(dir, reach);
        }
    }

    public void cleansePaths(){
        for (Path p: lineOfSight){
            p.cleanse();
        }
    }


    //Move Methods
    public boolean legality(Entity target){
        return !isAlly(target);
    }

    public boolean validMove(int x, int y){
        if (!validBounds(x, y)){
            return false;
        }

        for (Path p: lineOfSight){
            if (p.entities.getOrDefault(Chess.board[x][y], false)){
                return true;
            }
        }
        
        return false;
    }
    
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

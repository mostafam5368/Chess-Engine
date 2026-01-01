package piece;
import main.Chess;

import java.util.Set;
import java.util.LinkedHashSet;

public abstract class Piece extends Entity
{
    //Instance Variables
    protected int maxTilesPerMove;
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
        private Set<Entity> pieces;
        
        public Path(int[] dir, int m){
            direction = dir;
            max = m;
            pieces = new LinkedHashSet<>();

            build(row + direction[1], col + direction[0]);
        }
        
        public void build(int x, int y){
            if (!legalBounds(x, y)){
                return;
            }

            Entity current = Chess.board[x][y];
            
            pieces.add(current);
            current.seenBy.add(Piece.this);
            
            if (current.isOccupied() || pieces.size() >= max){
                return;
            }
            
            build(x + direction[1], y + direction[0]);
        }

        public void cleanse(){
            for (Entity e: pieces){
                e.seenBy.remove(Piece.this);
            }
        }
        
        public void rebuild(){
            pieces.clear();
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
            if (p.pieces.contains(target)){
                return p;
            }
        }
        
        return null;
    }

    public void buildPaths(){
        for (int i = 0; i < moveset.length; i++){
            int[] dir = moveset[i];
            lineOfSight[i] = new Path(dir, maxTilesPerMove);
        }
    }

    public void cleansePaths(){
        for (Path p: lineOfSight){
            p.cleanse();
        }
    }

    public void rebuildPaths(){
        for (Path p: lineOfSight){
            p.rebuild();
        }
    }

    public void updateLineOfSight(){
        cleansePaths();
        rebuildPaths();
    }
    

    //Move Methods
    public boolean legalMove(int x, int y){
        if (!legalBounds(x, y)){
            return false;
        }
        
        Entity target = Chess.board[x][y];
        
        if (inLineOfSight(target) != null && !isAlly(target)){
            return true;
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
        rebuildPaths();
    }
}

package piece;
import main.Chess;

import java.util.Set;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ArrayList;

public abstract class Piece extends Entity
{
    //Instance Variables
    protected int maxTilesPerMove;
    protected int[][] moveset;
    public List<Path> lineOfSight;
    

    //Constructors
    public Piece(String t, int r, int c){
        super(t, r, c);
        
        capture(Chess.board[r][c]);
        lineOfSight = new ArrayList<>();
    }
    
    
    protected class Path {
        private int[] direction;
        private int max;
        private Set<Entity> pieces;
        
        public Path(int[] dir, int m){
            direction = dir;
            max = m;
            
            pieces = new LinkedHashSet<>();
            buildPath(row + direction[1], col + direction[0]);
        }
        
        public void buildPath(int x, int y){
            if (!legalBounds(x, y)){
                return;
            }

            Entity current = Chess.board[x][y];
            
            pieces.add(current);
            current.seenBy.add(Piece.this);
            
            if (current.isOccupied() || pieces.size() >= max){
                return;
            }
            
            buildPath(x + direction[1], y + direction[0]);
        }

        public void clean(){
            for (Entity e: pieces){
                e.seenBy.remove(Piece.this);
            }
        }
        
        public void rebuild(){
            pieces.clear();
            buildPath(row + direction[1], col + direction[0]);
        }
        
        public void updateMax(int m){
            max = m;
            clean();
            rebuild();
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

    public void cleanAll(){
        for (Path p: lineOfSight){
            p.clean();
        }
    }

    public void rebuildAll(){
        for (Path p: lineOfSight){
            p.rebuild();
        }
    }

    public void updateLineOfSight(){
        cleanAll();
        rebuildAll();
    }
    

    //Move Methods
    public boolean legalMove(int x, int y){
        if (!legalBounds(x, y)){
            return false;
        }
        
        Entity target = Chess.board[x][y];
        
        if (!isAlly(target) && inLineOfSight(target) != null){
            return true;
        }
        
        return false;
    }
    
    @Override
    public void remove(){
        super.remove();
        cleanAll();
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
        rebuildAll();
    }
}

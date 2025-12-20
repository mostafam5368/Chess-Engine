package piece;
import java.util.ArrayList;

import main.Chess;

public class Piece
{
    //Instance Variables
    protected String team;
    protected int row, col;
    
    protected int maxTilesPerMove;
    protected int[][] moveset;
    protected ArrayList<Path> lineOfSight;
    
    //Constructors
    public Piece(){
        team = "";
        row = -1;
        col = -1;
    }
    
    public Piece(String t, int r, int c){
        team = t;
        row = r;
        col = c;
        
        Chess.board[row][col] = this;

        lineOfSight = new ArrayList<>();
    }
    
    
    protected class Path{
        private int[] direction;
        private int max;
        private ArrayList<Piece> pieces;
        
        public Path(){
            direction = new int[]{0, 0};
            max = 0;
            pieces = new ArrayList<>();
        }
        
        public Path(int[] dir, int m){
            direction = dir;
            max = m;
            
            pieces = new ArrayList<>();
            buildPath(row + direction[1], col + direction[0]);
        }
        
        public void buildPath(int x, int y){
            if (!legalBounds(x, y)){
                return;
            }
            
            pieces.add(Chess.board[x][y]);
            
            if (Chess.board[x][y].isOccupied() || pieces.size() >= max){
                return;
            }
            
            buildPath(x + direction[1], y + direction[0]);
        }
        
        public void update(){
            pieces.clear();
            buildPath(row + direction[1], col + direction[0]);
        }
        
        public void updateMax(int m){
            max = m;
            update();
        }
        
        public String toString(){
            String output = "";
            
            for (Piece p: pieces){
                output += "["+p.row+", "+p.col+"] - ";
            }
            return output;
        }
    }
    
    
    //Boolean Methods
    public boolean legalBounds(int x, int y){
        return (x < Chess.board.length && x >= 0) && (y < Chess.board[0].length && y >= 0);
    }
    public boolean isOccupied(){
        return !(this instanceof Tile);
    }
    public boolean isAlly(Piece target){
        return team.equals(target.team);
    }
    
    
    //Path Methods
    public boolean inLineOfSight(Piece target){
        for (Path p: lineOfSight){
            if (p.pieces.contains(target)){
                return true;
            }
        }
        
        return false;
    }
    
    public Piece getFirst(int x, int y, int[] dir){
        if (!legalBounds(x, y)){
            return null;
        }
        
        if (Chess.board[x][y].isOccupied()){
            return Chess.board[x][y];
        }
        
        return getFirst(x + dir[1], y + dir[0], dir);
    }
    

    //Update Methods
    public void updateLineOfSight(){
        for (Path p: lineOfSight){
            p.update();
        }
    }
    
    public void updateCrossing(){
        for (int[] dir: Chess.directions){
            Piece first = getFirst(row + dir[1], col + dir[0], dir);
            
            if (first != null){
                first.updateLineOfSight();
            }
        }
    }
    
    //Move Methods
    public boolean legalMove(int x, int y){
        if (!legalBounds(x, y)){
            return false;
        }
        
        Piece target = Chess.board[x][y];
        
        if (isAlly(target) || !inLineOfSight(target)){
            return false;
        }
        
        return true;
    }
    
    public void capture(Piece target){
        row = target.row;
        col = target.col;
        Chess.board[row][col] = this;
    }
    
    public void move(int x, int y){
        Chess.board[row][col] = new Tile(row, col);
        updateCrossing();
        
        capture(Chess.board[x][y]);
        
        updateCrossing();
        updateLineOfSight();
    }
}

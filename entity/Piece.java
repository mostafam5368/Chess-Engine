package entity;
import java.util.ArrayList;
import java.util.HashMap;
import game.Chess;

// This class represents one chess piece
public abstract class Piece extends Entity
{
    protected static int INF_REACH = Math.max(Chess.board.length - 1, Chess.board[0].length - 1);

    protected int reach;
    protected int[][] moveset;
    protected King king;
    public HashMap<Entity, Path> seenEntities;

    // Royal
    public Piece(String t, int r, int c){
        super(t, r, c);
        seenEntities = new HashMap<>();
    }
    
    // Non-Royal
    public Piece(King k, int r, int c){
        super(k.team, r, c);
        king = k;
        seenEntities = new HashMap<>();
    }
    
    // This class collects Entities in a direction on the board.
    public class Path {
        private int[] direction;
        private int maxSize;
        private Class<? extends Entity> captureRule;
        public ArrayList<Entity> contents;


        protected Path(int[] dir, int m){
            this(dir, m, Entity.class);
        }

        protected Path(int[] dir, int m, Class<? extends Entity> cr){
            direction = dir;
            maxSize = m;
            captureRule = cr;
            contents = new ArrayList<>();
            build(row + direction[1], col + direction[0]);
        }

        // This method represents one step in Path building.
        private void stepTo(Entity target){
            target.seenBy.put(Piece.this, canCapture(target));
            seenEntities.put(target, this);
            contents.add(target);
        }

        // This method represents one step in Path shrinking.
        private void stepFrom(Entity target){
            target.seenBy.remove(Piece.this);
            seenEntities.remove(target);
            contents.remove(target);
        }

        /*
            The purpose of this method is to traverse the board in one direction and collect Entities.
            Building stops once traversing off the board or reaching the first blocker.
        */
        protected void build(int x, int y){
            while (contents.size() < maxSize){
                if (!Chess.legalBounds(x, y)){
                    return;
                }

                stepTo(Chess.board[x][y]);

                if (Chess.board[x][y].isOccupied()){
                    return;
                }

                x += direction[1];
                y += direction[0];
            }
        }

        
        // The purpose of this method is to determine if an Entity is captureable along this Path.
        private boolean canCapture(Entity target){
            return !isAlly(target) && captureRule.isInstance(target);
        }

        // The purpose of this method is to build or shrink a Path to reflect a piece's vision on the board.
        protected void refreshAt(Entity e){
            Entity boardState = Chess.board[e.row][e.col];
            int entityIndex = contents.indexOf(e);

            contents.set(entityIndex, boardState);
            seenEntities.put(boardState, this);

            boardState.seenBy.put(Piece.this, canCapture(boardState));

            if (entityIndex < contents.size() - 1){
                // shrink to meet new blocker
                for (int i = contents.size() - 1; i > entityIndex; i--){
                    stepFrom(contents.get(i));
                }
            }
            else if (!contents.get(contents.size() - 1).isOccupied()){
                // build if there is no blocker
                build(boardState.row + direction[1], boardState.col + direction[0]);
            }
        }
    }
    

    // The purpose of this method is to build Paths in every direction the Piece is allowed.
    protected void buildPaths(){
        for (int[] dir: moveset){
            new Path(dir, reach);
        }
    }

    // The purpose of this method is to remove move legality from all Entities that can be seen.
    protected void blind(){
        for (Entity e: seenEntities.keySet()){
            e.seenBy.remove(this);
        }

        seenEntities.clear();
    }
    
    /*
        The purpose of this method is to complete a move/capture on a board.
        A boolean value is returned which represents if the move was completed with respect to check.
    */
    public boolean move(int x, int y){
        int startingRow = row;
        int startingCol = col;

        new Tile(row, col).place();

        Entity target = Chess.board[x][y];
        capture(target);

        if (inCheck()){
            target.place();

            capture(Chess.board[startingRow][startingCol]);
            buildPaths();

            return false;
        }

        Chess.materials.replace(team, Chess.materials.getOrDefault(team, 0) + target.materialValue);
        Chess.materials.replace(target.team, Chess.materials.getOrDefault(target.team, 0) - target.materialValue);

        buildPaths();
        return true;
    }

    // The purpose of this method is to determine if the king can be captured.
    public boolean inCheck(){
        return king.isCapturable();
    }

    @Override
    public void place(){
        super.place();
        buildPaths();
    }

    @Override
    public void removeFromBoard(){
        super.removeFromBoard();
        blind();
    }
}

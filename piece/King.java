package piece;

public final class King extends Piece
{
    public King(String t, int r, int c){
        super(t, r, c);
        reach = 1;
        
        moveset = new int[][]{
            {0,-1},{1,0},{0,1},{-1,0},
            {1,-1},{1,1},{-1,1},{-1,-1}
        };
        
        paths = new Path[moveset.length];
        buildPaths();
    }

    @Override
    public boolean inCheck(){
        return isCapturable();
    }

    public Piece pieceFromNotation(String move, Entity target){
        Class<?> type;

        switch (move.charAt(0)){
            case 'Q':
                type = Queen.class;
                break;
            case 'R':
                type = Rook.class;
                break;
            case 'N':
                type = Knight.class;
                break;
            case 'B':
                type = Bishop.class;
                break;
            case 'K':
                type = King.class;
                break;
            default:
                type = Pawn.class;
                break;
        }

        for (Piece piece: target.seenBy.keySet()){
            if (type.isInstance(piece) && isAlly(piece)){
                return piece;
            }
        }

        return null;
    }
    
    public String toString(){
        String str = "K";

        if (team.equals("black")){
            str = str.toLowerCase();
        }

        return str;
    }
}

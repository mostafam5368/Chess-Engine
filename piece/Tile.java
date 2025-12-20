package piece;
public class Tile extends Piece
{
    public Tile(int r, int c){
        super("", r, c);
    }
    
    public String toString(){
        return "_";
    }
}

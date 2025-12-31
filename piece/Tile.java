package piece;

public class Tile extends Entity
{
    public Tile(int r, int c){
        super("", r, c);
    }
    
    public String toString(){
        if (isSeen()){
            return "!";
        }

        return "_";
    }
}

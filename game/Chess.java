package game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import entity.*;

public class Chess
{
    public static Entity[][] board = new Entity[8][8];
    public static Scanner reader;

    public static King white = new King("White", 7, 4);
    public static King black = new King("Black", 0, 4);
    public static int turn = 0;

    public static HashMap<King, King> opponents = new HashMap<>();

    public static void play(){
        reader = new Scanner(System.in);

        opponents.put(white, black);
        opponents.put(black, white);

        prepBoard();
        fillBoard();

        // printBoard();
        // reader.nextLine();
        // clearScreen();

        King winner = null;
        
        while (true){
            playRound(white);

            if (black.inCheckmate()){
                winner = white;
                break;
            }
            
            playRound(black);

            if (white.inCheckmate()){
                winner = black;
                break;
            }
        }

        clearScreen();
        printBoard();
        
        if (winner != null){
            System.out.println(winner.team + " win.");
        }
        else {
            System.out.println("Draw.");
        }
    }

    public static void playRound(King player){
        clearScreen();
        printBoard();
        turn++;
        prompt(player);
    }

    public static boolean legalBounds(int x, int y){
        return (x < board.length && x >= 0) && (y < board[0].length && y >= 0);
    }

    private static void prepBoard(){
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                board[i][j] = new Tile(i, j);
            }
        }
    }
    
    private static void fillBoard(){
        white.place();
        black.place();

        for (int i = 0; i < board[6].length; i++){
            new Pawn(white, board.length - 2, i).place();
            new Pawn(black, 1, i).place();
        }

        new Rook(white, board.length - 1, 0).place();
        new Rook(black, 0, 0).place();

        new Knight(white, board.length - 1, 1).place();
        new Knight(black, 0, 1).place();

        new Bishop(white, board.length - 1, 2).place();
        new Bishop(black, 0, 2).place();

        new Queen(white, board.length - 1, 3).place();
        new Queen(black, 0, 3).place();

        new Bishop(white, board.length - 1, 5).place();
        new Bishop(black, 0, 5).place();

        new Knight(white, board.length - 1, 6).place();
        new Knight(black, 0, 6).place();

        new Rook(white, board.length - 1, 7).place();
        new Rook(black, 0, 7).place();
    }

    private static void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    private static void printBoard(){
        String blackMaterial = "";
        if (black.materialGained > white.materialGained) blackMaterial += "+" + (black.materialGained - white.materialGained);
        else blackMaterial += "--";

        String whiteMaterial = "";
        if (white.materialGained > black.materialGained) whiteMaterial += "+" + (white.materialGained - black.materialGained);
        else whiteMaterial += "--";

        System.out.println();
        String heightSpacing = "\n";
        String widthSpacing = "     ";

        if (turn % 2 == 0){   // white view
            System.out.println(blackMaterial);

            for (int i = 0; i < board.length; i++){
                System.out.print(board.length - i);

                for (Entity e: board[i]){
                    System.out.print(widthSpacing + e);
                }

                System.out.println(heightSpacing);
            }

            System.out.print(" ");

            for (int i = 'a'; i < 'a' + board[0].length; i++){
                System.out.print(widthSpacing + (char)i);
            }

            System.out.println();
            System.out.println(whiteMaterial);
        }
        else {  // black view
            System.out.println(whiteMaterial);

            for (int i = board.length - 1; i >= 0; i--){
                System.out.print(board.length - i);

                for (int j = board[i].length - 1; j >= 0; j--){
                    System.out.print(widthSpacing + board[i][j]);
                }

                System.out.println(heightSpacing);
            }

            System.out.print(" ");

            for (int i = 'a' + board[0].length - 1; i >= 'a'; i--){
                System.out.print(widthSpacing + (char)i);
            }

            System.out.println();
            System.out.println(blackMaterial);
        }
    }

    private static void prompt(King player){
        int x, y = 0;
        String move;
        ArrayList<Piece> potentials;

        do {
            do {
                System.out.print("> ");

                move = reader.nextLine();

                while (move.length() < 2){
                    move = reader.nextLine();
                }

                x = '8' - move.charAt(move.length() - 1);
                y = move.charAt(move.length() - 2) - 'a';
            } while (!legalBounds(x, y));

            Class<? extends Piece> type;

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

            String given;

            if (type == Pawn.class){
                given = move.substring(0, move.length() - 2);
            }
            else {
                given = move.substring(1, move.length() - 2);
            }
            
            potentials = disambiguate(board[x][y].capturableBy(player.team, type), given);
        } while (potentials.size() > 1 || potentials.isEmpty());

        Entity target = Chess.board[x][y];
        boolean tryMove = potentials.get(0).move(x, y);

        if (!tryMove){
            prompt(player);
        }

        if (target.materialValue > 0){
            player.materialGained += target.materialValue;
        }
    }

    private static ArrayList<Piece> disambiguate(ArrayList<Piece> potentials, String disambig){
        ArrayList<Piece> output = new ArrayList<>();
        int[] arr = new int[]{'.', '.'};

        for (int i = 0; i < disambig.length(); i++){
            char current = disambig.charAt(i);

            if (Character.isLetter(current)){
                arr[0] = current - 'a';
            }
            if (Character.isDigit(current)){
                arr[1] = '8' - current;
            }
        }

        for (Piece piece: potentials){
            if ((arr[0] == '.' || piece.onCol(arr[0])) && (arr[1] == '.' || piece.onRow(arr[1]))){
                output.add(piece);
            }
        }

        return output;
    }
}

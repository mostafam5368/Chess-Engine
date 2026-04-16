package game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import entity.*;

public class Chess
{
    public static Entity[][] board = new Entity[8][8];
    public static Scanner reader;

    public static King white = new King("white", 7, 4);
    public static King black = new King("black", 0, 4);

    public static HashMap<String, Integer> materials = new HashMap<>();

    public static void play(){
        reader = new Scanner(System.in);

        materials.put(white.team, 0);
        materials.put(black.team, 0);

        // printWelcomeScreen();
        fillBoard();

        while (true){
            printBoard();
            prompt(white);
            // printBoard();
            // prompt(black);
        }
    }

    public static boolean legalBounds(int x, int y){
        return (x < board.length && x >= 0) && (y < board[0].length && y >= 0);
    }

    private static void fillBoard(){
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                board[i][j] = new Tile(i, j);
            }
        }

        white.place();
        black.place();

        // for (int i = 0; i < board[6].length; i++){
        //     new Pawn(white, board.length - 2, i).place();
        //     new Pawn(black, 1, i).place();
        // }

        new Rook(white, board.length - 1, 0).place();
        // new Rook(black, 0, 0).place();

        // new Knight(white, board.length - 1, 1).place();
        // new Knight(black, 0, 1).place();

        // new Bishop(white, board.length - 1, 2).place();
        // new Bishop(black, 0, 2).place();

        // new Queen(white, board.length - 1, 3).place();
        // new Queen(black, 0, 3).place();

        // new Bishop(white, board.length - 1, 5).place();
        // new Bishop(black, 0, 5).place();

        // new Knight(white, board.length - 1, 6).place();
        // new Knight(black, 0, 6).place();

        new Rook(white, board.length - 1, 7).place();
        // new Rook(black, 0, 7).place();


        /*
            White: pawn at board[6][6] moves to board[5][6]
            Black: pawn at board[1][1] moves to board[2][1]

            White: knight at board[7][6] moves to board[5][5]
            Black: knight at board[0][1] moves to board[2][2]

            White: piece.move(x, y)
            Black: piece.move(board.length - x, board[0].length - y)

            move(int x, int y, Entity[][] board)
            printBoard(Entity[][] board)
         */
    }

    private static void printWelcomeScreen(){
        System.out.println("CHESS GAME");
        System.out.println("1. All rules of chess apply.");
        System.out.println("2. Enter moves using chess notation (ex. Qf3).");
        System.out.println("3. Disambiguate moves by file, rank, or both when necessary (ex. Nbd4).");
        System.out.println("4. Castle by entering a king move in a legal position (ex. Kg1).");
        System.out.println("5. Promote using the pop-up menu when pawn reaches last rank.");
        System.out.println();
        System.out.println("Enter any key to continue:");
        reader.nextLine();
    }

    private static void printBoard(){
        System.out.println();

        String blackMaterial = black.team.toUpperCase() + ": ";
        if (materials.get(black.team) > 0) blackMaterial += "+" + materials.get(black.team);
        else blackMaterial += "--";

        System.out.println(blackMaterial);

        for (int i = 0; i < board.length; i++){
            System.out.print(board.length - i);

            for (Entity e: board[i]){
                System.out.print("  " + e);
            }

            System.out.println();
        }

        System.out.print(" ");

        for (int i = 'a'; i < 'a' + board[0].length; i++){
            System.out.print("  "+ (char)i);
        }

        System.out.println();

        String whiteMaterial = white.team.toUpperCase() + ": ";
        if (materials.get(white.team) > 0) whiteMaterial += "+" + materials.get(white.team);
        else whiteMaterial += "--";

        System.out.println(whiteMaterial);
        System.out.println();
    }

    private static void prompt(King player){
        int x, y = 0;
        String move;
        ArrayList<Piece> potentials;

        do {
            System.out.print(player.team.toUpperCase() + " move: ");

            do {
                move = reader.nextLine();

                while (move.length() < 2){
                    move = reader.nextLine();
                }

                x = 56 - move.charAt(move.length() - 1);
                y = move.charAt(move.length() - 2) - 97;
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

        if (!potentials.get(0).move(x, y)){
            prompt(player);
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

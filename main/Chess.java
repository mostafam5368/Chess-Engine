package main;
import piece.*;

import java.util.Arrays;
import java.util.Scanner;

public class Chess
{
    public static Entity[][] board = new Entity[8][8];

    public static final int INF_REACH = Math.max(board.length - 1, board[0].length - 1);

    private static Scanner reader;

    public static void play(){
        reader = new Scanner(System.in);
        fillBoard();

        final King white = new King("white", 7, 4);
        // final King black = new King("black", 0, 4);

        Queen q1 = new Queen(white, 4, 6);
        Rook r1 = new Rook(white, 0, 0);
        Pawn p1 = new Pawn(white, 6, 5);
        
        while (true){
            System.out.println();
            printBoard();
            System.out.print("Your move: ");
            prompt(white);
        }
    }

    private static void fillBoard(){
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                board[i][j] = new Tile(i, j);
            }
        }

        /*
            final King white = new King("white", 7, 4);
            final King black = new King("black", 0, 4);

            for (int i = 0; i < board[6].length; i++){
                Pawn pawn = new Pawn(white, 6, i);
            }

            for (int i = 0; i < board[1].length; i++){
                Pawn pawn = new Pawn(black, 1, i);
            }

            Rook r1 = new Rook(white, 7, 0);
            Rook r2 = new Rook(black, 0, 0);

            Knight n1 = new Knight(white, 7, 1);
            Knight n2 = new Knight(black, 0, 1);

            Bishop b1 = new Bishop(white, 7, 2);
            Bishop b2 = new Bishop(black, 0, 2);

            Queen q1 = new Queen(white, 7, 3);
            Queen q2 = new Queen(black, 0, 3);

            Bishop b3 = new Bishop(white, 7, 5);
            Bishop b4 = new Bishop(black, 0, 5);

            Knight n3 = new Knight(white, 7, 6);
            Knight n4 = new Knight(black, 0, 6);

            Rook r3 = new Rook(white, 7, 7);
            Rook r4 = new Rook(black, 0, 7);
        */

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

    private static void printBoard(){
        for (int i = 0; i < board.length; i++){
            System.out.println(board.length - i + " " + Arrays.toString(board[i]));
        }

        System.out.print(" ");

        for (int i = 97; i < 97 + board.length; i++){
            System.out.print("  "+ (char)i);
        }

        System.out.println();
    }

    private static void prompt(King player){
        int x, y = 0;
        Piece toMove;

        do{
            String move;

            do{
                move = reader.nextLine();

                while (move.length() < 2){
                    move = reader.nextLine();
                }

                switch (move.length()){
                    case 3:    
                        x = 56 - move.charAt(2);
                        y = move.charAt(1) - 97;
                        break;

                    default:
                        x = 56 - move.charAt(1);
                        y = move.charAt(0) - 97;
                        break;
                }
            } while (!legalBounds(x, y));

            toMove = player.pieceFromNotation(move, board[x][y]);
        } while (!board[x][y].seenBy.getOrDefault(toMove, false));

        if (!toMove.move(x, y)){
            prompt(player);
        }
    }

    public static boolean legalBounds(int x, int y){
        return (x < board.length && x >= 0) && (y < board[0].length && y >= 0);
    }
}

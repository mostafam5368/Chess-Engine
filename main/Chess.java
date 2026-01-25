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

        Queen q1 = new Queen("white", 3, 0);
        Bishop b1 = new Bishop("black", 4, 4);
        Rook r1 = new Rook("black", 0, 5);

        while (true){
            printBoard();
            System.out.println(board[1][7].foundBy);
            prompt(q1);
        }
    }

    private static void fillBoard(){
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                board[i][j] = new Tile(i, j);
            }
        }
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

    private static void prompt(Piece piece){
        String move;
        int x, y;

        do{
            move = reader.nextLine();
            x = 56 - move.charAt(1);
            y = move.charAt(0) - 97;

        } while (!piece.legalMove(x, y));

        piece.move(x, y);
    }
}

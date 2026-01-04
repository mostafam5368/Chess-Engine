package main;
import piece.*;

import java.util.Arrays;
import java.util.Scanner;

public class Chess
{
    public static Entity[][] board;
    public static Scanner reader;

    public static void play() {
        board = new Entity[8][8];
        reader = new Scanner(System.in);
        fillBoard();

        Pawn q1 = new Pawn("white", 6, 3);
        Rook r1 = new Rook("black", 0, 3);
        Rook r2 = new Rook("black", 5, 2);
        
        while(true){
            printBoard();
            prompt(q1);
        }
    }

    public static void fillBoard(){
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                board[i][j] = new Tile(i, j);
            }
        }
    }

    public static void printBoard(){
        for (int i = 0; i < board.length; i++){
            System.out.println(board.length - i + " " + Arrays.toString(board[i]));
        }

        System.out.print(" ");

        for (int i = 97; i < 97 + board.length; i++){
            System.out.print("  "+ (char)i);
        }

        System.out.println();
    }

    public static void prompt(Piece piece){
        String move;
        int x, y;

        do{
            move = reader.nextLine();

            x = 56 - move.charAt(1);
            y = move.charAt(0) - 97;

        } while(!piece.legalMove(x, y));

        piece.move(x, y);
    }
}

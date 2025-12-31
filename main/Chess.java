package main;
import piece.*;

import java.util.Arrays;
import java.util.Scanner;

public class Chess
{
    public static Entity[][] board;

    public static void main(String[] args) {
        board = new Entity[8][8];
        Scanner reader = new Scanner(System.in);

        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                board[i][j] = new Tile(i, j);
            }
        }

        Queen q1 = new Queen("white", 6, 3);

        String move;
        
        while(true){
            printBoard();
            System.out.println(board[1][7].seenBy);

            move = reader.nextLine();

            while(!q1.legalMove(56 - move.charAt(1), move.charAt(0) - 97)){
                move = reader.nextLine();
            }

            q1.move(56 - move.charAt(1), move.charAt(0) - 97);
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
}

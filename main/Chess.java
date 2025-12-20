package main;
import java.util.Scanner;

import piece.Bishop;
import piece.Piece;
import piece.Queen;
import piece.Rook;
import piece.Tile;

import java.util.ArrayList;
import java.util.Arrays;

public class Chess
{
    public static Piece[][] board = new Piece[8][8];
    public static int[][] directions = new int[][]{
        {0,-1},{1,0},{0,1},{-1,0},
        {1,-1},{1,1},{-1,1},{-1,-1}
    };
    
    public static void main(String[] args){
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                Tile tile = new Tile(i, j);
            }
        }
        
        Queen queen = new Queen("white", 4, 4);
        Bishop bishop = new Bishop("white", 5, 3);
        Rook rook = new Rook("white", 6, 0);
        
        System.out.println("BEFORE MOVE:");
        System.out.println(queen);
        System.out.println(bishop);
        System.out.println(rook);
        
        System.out.println();
        queen.move(4, 0);
        
        System.out.println("AFTER MOVE:");
        System.out.println(queen);
        System.out.println(bishop);
        System.out.println(rook);
        
        for (Piece[] row: board){
            System.out.println(Arrays.toString(row));
        }
    }
}

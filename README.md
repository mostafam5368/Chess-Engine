# <div align="center">Chess Game</div>
<div align="center">
Chess program and move validator that simulates complete legal play inside the terminal. All possible positions in chess are attainable. No tutorials or generative AI used.
</div>

## Installation


## Features

### Functionality Overview

* __Standard Rules__: Supports all rules of chess.
    * Moving and capturing
    * Piece types and individual movement rules
    * Blocking restrictions
* __Special Rules__: Supports special moves allowed under certain conditions.
    * Castling
    * Promotion
    * En passant
* __King Rules__: Does not allow user to make a move that puts or keeps their king in check.


## Usage

### Input
* __Algebraic Notation__: Input moves by specifying piece type, except in the case of pawns, and destination square.
   * ex. `e4` = move pawn to e4
   * ex. `Qf3` = move queen to f3  
* __Ambiguity__: Reprompts user if more than one ally piece of the same type can capture the destination square. Specify piece file/rank.
   * ex. `Rae1` = move rook on file a to e1
   * ex. `Nc3e4` = move knight on c3 to e4  
* __Castling__: Castling is inputted as a king move as inspired by [Chess.com](https://www.chess.com/) (ex. Kg1).
* __Promoting__: Promote pawns by selecting the promotion piece from the pop-up menu when reaching the last rank.

### Opponent
This program does not feature an AI opponent.  This is a plan for future development. It is meant to be used by two people at the same screen with the board flipping its orientation every turn.


## Game Demonstration

### Castling
<img width="490" height="450" alt="20260512-0001-11 3730360" src="https://github.com/user-attachments/assets/5e1bfbf1-2f09-48a4-ac71-93219ee33f14" />

### En Passant
<img width="490" height="450" alt="20260511-2357-47 0637416" src="https://github.com/user-attachments/assets/68d6b854-3148-4e78-bcd5-8ef5e52e23dc" />

### Turns
<img width="490" height="450" alt="turnsmedium" src="https://github.com/user-attachments/assets/69c2d8e4-1ecc-467e-a7e3-6916415aaf93" />

## How piece vision works
Every square on the board keeps a dynamic list of what pieces can capture it. Move validation works by checking this list for the moving piece.

When a square on a path is affected by a move or capture, the piece the path belongs to is notified to rebuild that path. This maintains consistent play where pieces only have access to squares that they can actually see.

## Development Notebook
While working on this as my senior project, I was required to document my work issue by issue through the GitHub Projects feature. The issues feature more in-depth explanations, thought processes, challenges that I encountered, and the time it took me to add each feature.

Access GitHub Project here: [Mostafa PPP 2025](https://github.com/users/mostafam5368/projects/1)

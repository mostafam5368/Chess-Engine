# <div align="center">Chess Game</div>
<div align="center">
Program that simulates full legal play inside the terminal. All possible positions in chess are attainable. No generative AI used.
</div>

## Installation


## Features

### Functionality Overview

* __Standard Rules__: Supports all rules of standard chess.
    * Moving and capturing
    * Individual piece movement rules
    * Blocking restrictions
      
* __Special Rules__: Supports special moves in chess allowed under certain conditions.
    * En passant
    * Castling
    * Promotion
      
* __King Rules__: Program does not allow user to make a move which puts or keeps their king in check, ensuring total legal play. 


## Usage

### Input
* __Algebraic Notation__: Supports algebraic chess notation as input for a move.
    * ex. An input of “e4” → move pawn to e4
    * ex. An input of “Qf3” → move queen to f3
      
* __Ambiguity__: Reprompts the user if more than one type of the same piece can capture the desired square. Include disambiguation in algebraic notation.
    * ex. An input of “xxxx” → move queen on column x to xx
    * ex. An input of “xxxx” → move queen on row x to xx
    * ex. An input of “xxxx” → move queen on xx to xx

### Opponent
This program does not feature an AI opponent. That is a plan for future development. It is meant to be used by two people at the same screen with the board flipping its orientation with every turn.
      
```
more code
```


## Game Demonstration

```
code/screenshot for moving
```


## How piece vision works
Every square on the board keeps a list of what pieces can capture it. When it comes time for a move, the square's list of pieces is checked for the moving piece.

When a square on a path is affected by a move or capture, the piece the path belongs to is notified to rebuild that path. This maintains consistent and legal play where pieces can only capture squares they are actually allowed to.

```
more code
```


## Development Notebook
While working on this project, I was required to document my work issue by issue through the GitHub Projects feature. The issues feature more in-depth explanations, thought processes, and challenges I encountered while writing this program.

Access GitHub Project here: [Mostafa, Marawan - Chess Dev Notebook]()

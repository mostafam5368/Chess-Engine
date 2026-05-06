# <div align="center">Chess Game</div>
<div align="center">
Program that simulates full legal play inside the terminal. All possible positions in chess are attainable.
</div>

## Installation


## Features

#### Functionality Overview

* __Standard Rules__: Supports all rules of standard chess.
    * move validation, line of sight building, etc
* __Special Rules__: Supports special moves allowed under certain conditions.
    * En passant, castling, promotion


## Usage

#### Input
* __Algebraic Notation__: Qh4.
* __Ambiguity__: when more than one piece of the same type can capture the same square.
    * Disambiguating by row, column, or both.
      
```
more code
```


## Game Demonstration

```
code/screenshot for moving
```


## How piece vision works
This section explains the core logic behind legal play.

#### Move Validation
Every square on the board keeps a list of what pieces can capture it. When it comes time for a move, the square's list of pieces is checked for the moving piece.
#### Path Updates
When a square on a path is affected by a move or capture, the piece the path belongs to is notified to rebuild that path. This maintains consistent and legal play.
#### The King
Every piece stores its team's king as a variable. A piece is not allowed to make a move that puts or keeps its king in check.

```
more code
```


## Development Notebook
While working on this project, I was required to document my work issue by issue through the GitHub Projects feature. The issues feature more in-depth explanations, thought processes, and challenges I encountered while writing this program.

Access GitHub Project here: [Mostafa, Marawan - Chess Dev Notebook]()

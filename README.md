# Team-Silver

# Chess Game in Java

## Overview

This project is a chess game implemented in Java. It allows two players to play chess locally using text-based input and a chessboard displayed in the console.

The game starts with White and alternates between White and Black after each valid move. It validates moves, detects check, and identifies checkmate.

## Features

- Displays the chessboard in the console.
- Supports the standard chess pieces: King, Queen, Rook, Bishop, Knight, and Pawn.
- Calculates possible moves for each chess piece.
- Validates player moves.
- Prevents players from making invalid moves.
- Detects when a king is in check.
- Detects checkmate and announces the winner.
- Uses colored console output to highlight turns, invalid moves, check, and checkmate.
- Includes Javadoc comments to document classes, constructors, and methods.

## Technologies Used

- Java
- Java standard library
- `java.util.Scanner` for reading player input
- ANSI escape codes for colored console output
- Javadoc for code documentation

## Project Structure

```text
ChessProject/
├── src/
│   ├── Main.java
│   ├── game/
│   │   ├── Board.java
│   │   ├── BoardPrinter.java
│   │   ├── CheckValidator.java
│   │   └── MoveHandler.java
│   └── pieces/
│       ├── Piece.java
│       ├── PieceColor.java
│       ├── Position.java
│       ├── King.java
│       ├── Queen.java
│       ├── Rook.java
│       ├── Bishop.java
│       ├── Knight.java
│       └── Pawn.java
└── docs/
    └── index.html
```

## Requirements

- Java Development Kit (JDK) installed.
- An IDE such as IntelliJ IDEA, Eclipse, or VS Code, or a terminal for compiling and running the project.
- A console that supports ANSI escape codes for colored output.

## How to Run

### Using an IDE

1. Open the project in your Java IDE.
2. Locate `Main.java`.
3. Run the `main()` method.
4. Follow the instructions displayed in the console.

### Using the terminal

If your source files are organized under `src`, compile the Java files:

```bash
javac -d out src/Main.java src/game/*.java src/pieces/*.java
```

Then start the game:

```bash
java -cp out Main
```

Run these commands from the project's root directory. They assume `Main.java` is in the unnamed package and the other files are in the `game` and `pieces` packages.

## How to Play

1. Run the program.
2. The chessboard will be displayed in the console.
3. The current player's turn will be shown.
4. Enter your move using the starting and destination squares, separated by a space.
5. For example:

   ```text
   e2 e4
   ```

   This moves the piece from square `e2` to square `e4`, provided the move is legal.

6. If a move is invalid, the game asks the same player to try again.
7. After a successful move, the game checks whether the opposing king is in check or checkmate.
8. The game ends when checkmate is detected and the winner is announced.

## Code Documentation

The Java source files contain Javadoc comments that describe classes, constructors, and methods.

To generate HTML documentation, use the Javadoc generator in your IDE or the Java `javadoc` command.

Generated documentation can be stored in the `docs/` directory and viewed by opening `docs/index.html` in a web browser.

## Limitations

- The game is played by two local players in the console.
- Moves are entered using chessboard coordinates, such as `e2 e4`.
- The interface depends on console output rather than a graphical chessboard.
- Only the chess rules implemented in the current source code are supported.

## Future Improvements

Possible enhancements include:

- Adding castling and en passant.
- Adding pawn promotion.
- Improving move input validation.
- Adding a graphical user interface.
- Adding automated tests for move validation and checkmate detection.

## Author

Class - CS 3354.R01
Team member - Sufiyan Habeebullah Mohammad

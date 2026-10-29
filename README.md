# Team-Silver

# Chess Game in Java

## Overview
This project is a two-player, console-based chess game written in Java. It is for casual players and students who want to play chess on one computer without a graphical interface. Players type moves in chess notation (for example `e2 e4`) and the board is redrawn in the console after every move.

White moves first, and turns alternate after each valid move. The game validates moves, detects check, and detects checkmate.

## Requirements
- **Java version:** JDK 8 or later (the code uses no newer language features; JDK 17 or later is recommended).
- **Entry-point class:** `Main` (in `src/Main.java`, in the unnamed package). Start the game by running its `main()` method.
- A console that supports ANSI escape codes for colored output. Most Mac and Linux terminals, IntelliJ, and VS Code do. On older Windows consoles, colors may show as raw characters.

## Project Structure
```
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

## Setup and Run Instructions

### Using an IDE
1. Open the project in IntelliJ IDEA, Eclipse, or VS Code.
2. Mark `src` as the source folder.
3. Open `Main.java` and run the `main()` method.
4. Type moves in the console when prompted.

### Using the terminal
Run these from the project's root directory.

Compile:
```
javac -d out src/Main.java src/game/*.java src/pieces/*.java
```

Run:
```
java -cp out Main
```

## How to Play
1. Run the program. The board is shown, along with whose turn it is.
2. Enter a move as the starting square and the destination square, separated by a space, then press Enter:
   ```
   e2 e4
   ```
3. Files are letters `a`-`h` and ranks are numbers `1`-`8`. Input is not case sensitive.
4. If the move is invalid, the game prints a message and asks the same player to try again.
5. After each successful move, the game checks for check and checkmate.
6. The game ends when checkmate is detected and the winner is announced.

### Sample input
| Input | Result |
|---|---|
| `e2 e4` | White pawn moves two squares |
| `g8 f6` | Black knight moves |
| `e4 d5` | A pawn captures if an opposing piece is on d5 |
| `z9 e4` | Rejected: "Invalid square" |
| `e7 e5` on White's turn | Rejected: "Not your piece" |
| `a1 a3` at the start | Rejected: "Illegal move (not in move list)" |

## Completed Features
- Displays an 8x8 chessboard in the console with colored squares, file letters (A-H) and rank numbers (1-8).
- Sets up all 32 pieces in their standard starting positions.
- Supports King, Queen, Rook, Bishop, Knight and Pawn, each with its own movement rules.
- Pawns move one or two squares on their first move and capture diagonally.
- Sliding pieces (Rook, Bishop, Queen) stop at blocking pieces and can capture opposing pieces.
- Alternates turns between White and Black after each valid move.
- Validates input and rejects invalid squares, empty squares, the opponent's pieces, and moves a piece cannot make.
- Supports captures and records every move and capture in a history printed under the board.
- Detects when a king is in check and announces it.
- Rejects any move that leaves the player's own king in check, or that does not resolve an existing check.
- Detects checkmate, announces the winner and ends the game.
- Uses colored console output for turns, errors, check and checkmate.
- Includes Javadoc comments on classes, constructors and methods.

## Known Limitations
- Castling, en passant and pawn promotion are not implemented. A pawn that reaches the last rank stays a pawn.
- Stalemate (no legal moves but not in check) is not detected.
- There is no draw detection (for example, repetition or insufficient material), no resignation and no undo.
- Each move must be two squares separated by whitespace. If fewer than two words are entered, the program waits for more input.
- The interface is console-only; there is no graphical board.
- There are no automated unit tests yet.

## Technologies Used
- Java and the Java standard library
- `java.util.Scanner` for reading input
- ANSI escape codes for colored output
- Javadoc for documentation

## Code Documentation
The source files have Javadoc comments. To generate HTML documentation, use your IDE's Javadoc tool or run `javadoc` from the terminal. Generated documentation goes in `docs/` and can be viewed by opening `docs/index.html` in a browser.

## Future Improvements
- Add castling, en passant and pawn promotion.
- Add stalemate and draw detection.
- Make move input more forgiving (case, extra spaces, notation like `e4`).
- Add a graphical user interface.
- Add automated tests for move validation and checkmate detection.

## Author

Class - CS 3354.R01
Team member - Sufiyan Habeebullah Mohammad

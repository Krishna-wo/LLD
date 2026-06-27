# Tic Tac Toe – Low Level Design (Java)

## 1. Problem Summary

Two players alternate placing their symbols (X / O) on an N×N grid.
The game ends when one player fills an entire row, column, or diagonal, or when the board is full (draw).

---

## 2. Core Entities

| Class / Enum | Responsibility |
|---|---|
| `Symbol` | Enum – `X` or `O` |
| `GameStatus` | Enum – `IN_PROGRESS`, `WIN`, `DRAW` |
| `Cell` | One square on the board; holds an optional `Symbol` |
| `Board` | N×N grid of `Cell`s; validates moves and checks win/draw |
| `Player` | Name + Symbol |
| `Game` | Orchestrates turns, delegates to `Board`, tracks `GameStatus` |
| `TicTacToeDemo` | Entry point / simulation |

---

## 3. UML Class Diagram

```
+------------------+        +-------------------+
|    <<enum>>      |        |    <<enum>>        |
|     Symbol       |        |    GameStatus      |
|------------------|        |-------------------|
|  X               |        |  IN_PROGRESS       |
|  O               |        |  WIN               |
+------------------+        |  DRAW              |
                             +-------------------+

+-------------------+
|      Player       |
|-------------------|
| - name: String    |
| - symbol: Symbol  |
|-------------------|
| + getName()       |
| + getSymbol()     |
+-------------------+

+---------------------------+           +----------------------------------+
|           Cell            |  1     *  |              Board               |
|---------------------------|<----------|----------------------------------|
| - row: int                |           | - size: int                      |
| - col: int                |           | - grid: Cell[][]                 |
| - symbol: Symbol          |           |----------------------------------|
|---------------------------|           | + isCellEmpty(row, col): boolean |
| + getSymbol(): Symbol     |           | + isValidPosition(r,c): boolean  |
| + setSymbol(Symbol)       |           | + setCell(row, col, Symbol)      |
| + isEmpty(): boolean      |           | + isFull(): boolean              |
+---------------------------+           | + checkWin(Symbol): boolean      |
                                        | + printBoard()                   |
                                        +----------------------------------+

+------------------------------------------+
|                   Game                   |
|------------------------------------------|
| - board: Board                           |
| - players: Player[2]                     |
| - currentPlayerIndex: int                |
| - status: GameStatus                     |
|------------------------------------------|
| + play()                                 |
| + makeMove(row, col): boolean            |
| - checkWin(): boolean                    |
| - checkDraw(): boolean                   |
| - switchPlayer()                         |
| + getCurrentPlayer(): Player             |
| + getStatus(): GameStatus                |
+------------------------------------------+
```

---

## 4. Class Descriptions

### `Symbol` (enum)
```java
public enum Symbol { X, O }
```
Simple enum used as the marker placed in a cell.

---

### `GameStatus` (enum)
```java
public enum GameStatus { IN_PROGRESS, WIN, DRAW }
```
Represents the current state of the game.

---

### `Cell`
```java
public class Cell {
    private final int row, col;
    private Symbol symbol;          // null = empty

    public boolean isEmpty()        { return symbol == null; }
    public Symbol  getSymbol()      { return symbol; }
    public void    setSymbol(Symbol s) { this.symbol = s; }
}
```
Holds one square. Starts empty (`symbol == null`).

---

### `Player`
```java
public class Player {
    private final String name;
    private final Symbol symbol;
    // getters only – immutable after construction
}
```
Immutable value object. No game logic lives here.

---

### `Board`
```java
public class Board {
    private final int size;
    private final Cell[][] grid;

    public boolean isCellEmpty(int row, int col)
    public boolean isValidPosition(int row, int col)
    public void    setCell(int row, int col, Symbol symbol)
    public boolean isFull()
    public boolean checkWin(Symbol symbol)   // rows, cols, diagonals
    public void    printBoard()
}
```

**Win-check logic** (`checkWin`):
1. For each row – all cells match `symbol`? → win.
2. For each column – all cells match `symbol`? → win.
3. Main diagonal (top-left → bottom-right) – all match? → win.
4. Anti-diagonal (top-right → bottom-left) – all match? → win.

This is O(N) per check and works for any N.

---

### `Game`
```java
public class Game {
    private final Board   board;
    private final Player[] players;          // index 0 and 1
    private int            currentPlayerIndex;
    private GameStatus     status;

    public Game(Player p1, Player p2)              // 3×3 default
    public Game(Player p1, Player p2, int size)    // custom N×N

    public void    play()                          // interactive console loop
    public boolean makeMove(int row, int col)      // programmatic / testable
}
```

**`makeMove` flow:**
```
validate position in bounds
  └─ NO  → print error, return false
validate cell is empty
  └─ NO  → print error, return false
place symbol on board
checkWin?
  └─ YES → status = WIN,  return true
checkDraw?
  └─ YES → status = DRAW, return true
switchPlayer()
return true
```

**`play` flow (console):**
```
print welcome
WHILE status == IN_PROGRESS:
    printBoard()
    prompt current player for row, col
    validate input (try/catch for non-integer)
    makeMove(row, col)
printBoard()
print winner or draw
```

---

## 5. Sequence Diagram – One Turn

```
Player        Game              Board
  |             |                 |
  |--makeMove-->|                 |
  |             |--isValidPos?--->|
  |             |<--true----------|
  |             |--isCellEmpty?-->|
  |             |<--true----------|
  |             |--setCell()----->|
  |             |--checkWin()---->|
  |             |<--false---------|
  |             |--isFull()------>|
  |             |<--false---------|
  |             |--switchPlayer() |
  |<--true------|                 |
```

---

## 6. Design Decisions

| Decision | Rationale |
|---|---|
| `Board` owns all win/draw logic | Keeps `Game` thin; board is independently testable |
| `Cell` is a simple wrapper | Makes it easy to extend (e.g. add color, timestamp) |
| `makeMove` returns `boolean` | Allows the demo / tests to call it programmatically without Scanner |
| `boardSize` as constructor param | Any N×N board with zero code changes |
| No `static` state | Fully object-oriented; multiple `Game` instances can coexist |

---

## 7. File Structure

```
tictactoe/
└── src/
    └── tictactoe/
        ├── Symbol.java
        ├── GameStatus.java
        ├── Cell.java
        ├── Player.java
        ├── Board.java
        ├── Game.java
        └── TicTacToeDemo.java
```

---

## 8. How to Compile and Run

```bash
# From the tictactoe/ root directory

# Compile
javac -d out src/tictactoe/*.java

# Run interactive game
java -cp out tictactoe.TicTacToeDemo
```

Sample console session:

```
=== Tic Tac Toe ===
Alice (X) vs Bob (O)

    0   1   2
 0 |   |   |   |
   +---+---+---+
 1 |   |   |   |
   +---+---+---+
 2 |   |   |   |
   +---+---+---+

Alice (X)'s turn. Enter row and col (e.g. 1 2):
> 0 0
...
Game Over! Winner: Alice (X)
```

---

## 9. Extension Points

- **Larger board** – pass a different size to the `Game` constructor: `new Game(p1, p2, 5)`.
- **AI player** – add a `ComputerPlayer extends Player` with a `chooseMove(Board)` method; `Game.play()` can call it instead of reading from `Scanner`.
- **GUI** – replace the `Scanner` loop in `play()` with a Swing/JavaFX click handler; `makeMove(row, col)` stays unchanged.
- **Game history** – store `(row, col, symbol)` tuples in a list inside `Game` for undo/replay support.

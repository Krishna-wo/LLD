

/**
 * TicTacToeDemo - Entry point for the Tic Tac Toe game.
 *
 * Run this class to start an interactive game in the console.
 *
 * Example compile & run:
 *   javac -d out src/tictactoe/*.java
 *   java -cp out tictactoe.TicTacToeDemo
 */
public class TicTacToeDemo {

    public static void main(String[] args) {
        Player p1 = new Player("Alice", Symbol.X);
        Player p2 = new Player("Bob", Symbol.O);

        // Change the third argument to play on a larger board, e.g. new Game(p1, p2, 4)
        Game game = new Game(p1, p2);
        game.play();
    }
}

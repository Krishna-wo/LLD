
import java.util.Scanner;

public class Game {

    private final Board board;
    private final Player[] players;
    private int currentPlayerIndex;
    private GameStatus status;

    public Game(Player p1, Player p2) {
        this(p1, p2, 3); // default 3x3 board
    }

    public Game(Player p1, Player p2, int boardSize) {
        this.board = new Board(boardSize);
        this.players = new Player[]{p1, p2};
        this.currentPlayerIndex = 0;
        this.status = GameStatus.IN_PROGRESS;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Tic Tac Toe ===");
        System.out.println(players[0] + " vs " + players[1]);

        while (status == GameStatus.IN_PROGRESS) {
            board.printBoard();
            Player current = getCurrentPlayer();
            System.out.println(current + "'s turn. Enter row and col (e.g. 1 2): ");

            int row, col;
            try {
                row = scanner.nextInt();
                col = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter two integers.");
                scanner.nextLine(); // clear buffer
                continue;
            }

            makeMove(row, col);
        }

        board.printBoard();

        if (status == GameStatus.WIN) {
            // The player who just moved won — that's the one before switching
            Player winner = players[(currentPlayerIndex + 1) % 2];
            System.out.println("Game Over! Winner: " + winner);
        } else {
            System.out.println("Game Over! It's a draw.");
        }

        scanner.close();
    }

    public boolean makeMove(int row, int col) {
        if (status != GameStatus.IN_PROGRESS) {
            System.out.println("Game is already over.");
            return false;
        }

        if (!board.isValidPosition(row, col)) {
            System.out.println("Invalid position. Row and col must be between 0 and " + (board.getSize() - 1));
            return false;
        }

        if (!board.isCellEmpty(row, col)) {
            System.out.println("Cell (" + row + ", " + col + ") is already occupied. Choose another.");
            return false;
        }

        Player current = getCurrentPlayer();
        board.setCell(row, col, current.getSymbol());

        if (checkWin()) {
            status = GameStatus.WIN;
        } else if (checkDraw()) {
            status = GameStatus.DRAW;
        } else {
            switchPlayer();
        }

        return true;
    }

    private boolean checkWin() {
        return board.checkWin(getCurrentPlayer().getSymbol());
    }

    private boolean checkDraw() {
        return board.isFull();
    }

    private void switchPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
    }

    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    public GameStatus getStatus() {
        return status;
    }

    public Board getBoard() {
        return board;
    }
}

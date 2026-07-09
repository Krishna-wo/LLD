

public class Board {

    private final int size;
    private final Cell[][] grid;

    public Board(int size) {
        this.size = size;
        this.grid = new Cell[size][size];
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                grid[r][c] = new Cell(r, c);
            }
        }
    }

    public boolean isCellEmpty(int row, int col) {
        return grid[row][col].isEmpty();
    }

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    public void setCell(int row, int col, Symbol symbol) {
        grid[row][col].setSymbol(symbol);
    }

    public boolean isFull() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (grid[r][c].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check if the given symbol has won on the board.
     * Checks all rows, columns, and both diagonals.
     */
    public boolean checkWin(Symbol symbol) {
        // Check rows
        for (int r = 0; r < size; r++) {
            if (checkRow(r, symbol)) return true;
        }
        // Check columns
        for (int c = 0; c < size; c++) {
            if (checkCol(c, symbol)) return true;
        }
        // Check diagonals
        return checkMainDiagonal(symbol) || checkAntiDiagonal(symbol);
    }

    private boolean checkRow(int row, Symbol symbol) {
        for (int c = 0; c < size; c++) {
            if (grid[row][c].getSymbol() != symbol) return false;
        }
        return true;
    }

    private boolean checkCol(int col, Symbol symbol) {
        for (int r = 0; r < size; r++) {
            if (grid[r][col].getSymbol() != symbol) return false;
        }
        return true;
    }

    private boolean checkMainDiagonal(Symbol symbol) {
        for (int i = 0; i < size; i++) {
            if (grid[i][i].getSymbol() != symbol) return false;
        }
        return true;
    }

    private boolean checkAntiDiagonal(Symbol symbol) {
        for (int i = 0; i < size; i++) {
            if (grid[i][size - 1 - i].getSymbol() != symbol) return false;
        }
        return true;
    }

    public void printBoard() {
        System.out.println();
        // Column index header
        System.out.print("    ");
        for (int c = 0; c < size; c++) {
            System.out.print(" " + c + "  ");
        }
        System.out.println();

        for (int r = 0; r < size; r++) {
            System.out.print(" " + r + " |");
            for (int c = 0; c < size; c++) {
                Symbol s = grid[r][c].getSymbol();
                String cell = (s == null) ? " " : s.toString();
                System.out.print(" " + cell + " |");
            }
            System.out.println();
            // Row separator
            System.out.print("   ");
            System.out.println("+---".repeat(size) + "+");
        }
        System.out.println();
    }

    public int getSize() {
        return size;
    }
}

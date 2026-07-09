
public class Cell {

    private final int row;
    private final int col;
    private Symbol symbol;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.symbol = null; // empty by default
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public boolean isEmpty() {
        return symbol == null;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}

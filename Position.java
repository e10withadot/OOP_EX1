/**
 * Represents an (x,y) coordinate position.
 */
public class Position {
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the x coordinate.
     * @return x coordinate
     */
    public int row(){
        return this.row;
    }

    /**
     * Returns the y coordinate.
     * @return y coordinate
     */
    public int col(){
        return this.col;
    }
}
public class Position {
    private Double row;
    private Double col;

    public Position(Double row, Double col) {
        this.row = row;
        this.col = col;
    }

    public Double row(){
        return this.row;
    }

    public Double col(){
        return this.col;
    }
}

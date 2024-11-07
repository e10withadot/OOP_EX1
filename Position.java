public class Position {
    private double row;
    private double col;

    public Position(double row, double col) {
        this.row = row;
        this.col = col;
    }

    public double row(){
        return this.row;
    }

    public double col(){
        return this.col;
    }
}
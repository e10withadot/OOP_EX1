public class Position {
    protected double row;
    protected double col;

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

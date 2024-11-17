/**
 * The Move class represents the placement of a disc on the board.
 */
public class Move {
    private Position position;
    private Disc disc;

    public Move(Position position, Disc disc){
        this.position=position;
        this.disc=disc;
    }

    /**
     * Returns the position where the disc was placed.
     * @return Position object
     */
    public Position position(){
        return this.position;
    }

    /**
     * Returns the disc that was moved.
     * @return Disc object
     */
    public Disc disc(){
        return this.disc;
    }
}

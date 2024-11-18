import java.util.ArrayList;
import java.util.List;

/**
 * The Move class represents the placement of a disc on the board.
 */
public class Move {
    private Position position;
    private Disc disc;
    private List<Position> flips;

    public Move(Position position, Disc disc) {
        this.position=position;
        this.disc=disc;
        this.flips = new ArrayList<Position>();
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

    /**
     * Returns the flips performed in this move.
     * @return List of Position objects
     */
    public List<Position> flips(){
        return this.flips;
    }

    /**
     * Add a new flip position.
     * @param f Position object
     */
    public void addFlip(Position f){
        this.flips.add(f);
    }
}

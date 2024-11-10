/**
 * The basic Reversi disc. Flips over any disc in proximity if there is a disc on the opposing side. Can be flipped.
 */
public class SimpleDisc implements Disc {
    protected Player owner;

    public SimpleDisc(Player owner) {
        this.owner = owner;
    }

    public Player getOwner(){
        return this.owner;
    }

    public void setOwner(Player newOwner){
        this.owner= newOwner;
    }

    public String getType(){
        return "⬤";
    }
}
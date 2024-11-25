/**
 * The basic Reversi disc. Flips over any disc in proximity if there is a disc on the opposing side. Can be flipped.
 */
public class SimpleDisc extends Disc {
    public SimpleDisc(Player owner){
        super(owner);
    }
    
    @Override
    public String getType(){
        return "⬤";
    }
}
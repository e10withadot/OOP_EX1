/**
 * An unflippable disc. Works like a normal disc, but can't be flipped.
 */
public class UnflippableDisc extends SimpleDisc {
    public UnflippableDisc(Player owner) {
        super(owner);
    }
    
    @Override
    public String getType(){
        return "⭕";
    }
}
/**
 * An unflippable disc. Works like a normal disc, but can't be flipped.
 */
public class UnflippableDisc extends Disc {
    public UnflippableDisc(Player owner) {
        super(owner);
    }
    
    @Override
    public String getType(){
        return "⭕";
    }

    @Override
    public boolean reduce(){
        if(owner.getNumber_of_unflippedable() == 0) return false;
        owner.reduce_unflippedable();
        return true;
    }

    @Override
    public void restore(){
        owner.add_unflippedable();
    }
    
    @Override
    public Disc copy(){
        return new UnflippableDisc(owner);
    }
}
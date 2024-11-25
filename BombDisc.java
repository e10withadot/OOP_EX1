/**
 * A bomb disc. Flips any nearby disc, and produces a chain reaction with other bomb discs.
 */
public class BombDisc extends Disc {
    public BombDisc(Player owner) {
        super(owner);
    }
    
    @Override
    public String getType(){
        return "💣";
    }

    @Override
    public boolean reduce(){
        if(owner.getNumber_of_bombs() == 0) return false;
        owner.reduce_bomb();
        return true;
    }

    @Override
    public void restore(){
        owner.add_bomb();
    }
}
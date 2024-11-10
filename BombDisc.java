/**
 * A bomb disc. Flips any nearby disc, and produces a chain reaction with other bomb discs.
 */
public class BombDisc extends SimpleDisc {
    public BombDisc(Player owner) {
        super(owner);
    }
    
    @Override
    public String getType(){
        return "💣";
    }
}
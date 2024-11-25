
public class HumanPlayer extends Player {

    public HumanPlayer(boolean b) {
        super(b);
    }

    @Override
    boolean isHuman() {
        return true;
    }

    @Override
    public Player copy() {
        Player p = new HumanPlayer(isPlayerOne);
        return p;
    }
}

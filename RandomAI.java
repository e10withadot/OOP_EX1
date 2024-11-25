import java.util.ArrayList;
import java.util.List;

public class RandomAI extends AIPlayer {

    
    public RandomAI(boolean b) {
        super(b);
    }
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        // type of disc
        List<Disc> types = new ArrayList<>();
        // add simple disc
        types.add(new SimpleDisc(this));
        if(getNumber_of_unflippedable() > 0)
            // add unflippable disc
            types.add(new UnflippableDisc(this));
        if(getNumber_of_bombs() > 0)
            // add bomb disc
            types.add(new BombDisc(this));
        // choose random type
        Disc disc = types.get((int)(Math.random()*types.size()-1));
        // choose random position
        List<Position> moves =  gameStatus.ValidMoves();
        int rand_pos = (int) (Math.random()*moves.size()-1);
        return new Move(moves.get(rand_pos), disc);
    }

    @Override
    public Player copy() {
        Player p = new RandomAI(isPlayerOne);
        return p;
    }
}
import java.util.List;

public class RandomAI extends AIPlayer {

    
    public RandomAI(boolean b) {
        super(b);
    }
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        Disc[] types = {new SimpleDisc(this), new UnflippableDisc(this), new BombDisc(this)};
        Disc disc = types[(int)(Math.random()*types.length-1)];
        // If the special disks are finished, switch to a regular disk
        if(!disc.reduce()) {
            disc = types[0];
        }
        //Select a random move from all the available options
        int rand_pos;
        List<Position> moves =  gameStatus.ValidMoves();
        rand_pos = (int) (Math.random()*moves.size()-1);
        return new Move(moves.get(rand_pos), disc);
    }
}
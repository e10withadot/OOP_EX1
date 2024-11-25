import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GreedyAI extends AIPlayer {
    
    public GreedyAI(boolean b) {
        super(b);
    }
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        Disc disc = new SimpleDisc(this);
        int max_flip =0;
        // search the move with most flip;
        List<Position> moves = gameStatus.ValidMoves();
        List<Position> greatestMoves= new ArrayList<>();
        for (int i = 0; i < moves.size(); i++) {
            Position pos= moves.get(i);
            if (gameStatus.countFlips(pos)>max_flip){
                max_flip = gameStatus.countFlips(moves.get(i));
            }
        }
        for (int i = 0; i < moves.size(); i++) {
            Position pos= moves.get(i);
            if(gameStatus.countFlips(pos)==max_flip)
                greatestMoves.add(pos);
        }
        greatestMoves.sort(Comparator.reverseOrder());
        return new Move(greatestMoves.getFirst(), disc);
    }

    @Override
    public Player copy() {
        Player p = new GreedyAI(isPlayerOne);
        return p;
    }
}

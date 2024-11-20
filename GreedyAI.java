import java.util.List;

public class GreedyAI extends AIPlayer {
    
    public GreedyAI(boolean b) {
        super(b);
    }
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        int max_flip =0;
        // search the move with the most fli;
        List<Position> moves =  gameStatus.ValidMoves();
        for (int i = 0; i < moves.size(); i++) {
            if (gameStatus.countFlips(moves.get(i))>max_flip){
                max_flip = gameStatus.countFlips(moves.get(i));
            }
        }

    }

}

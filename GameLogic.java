import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameLogic implements PlayableLogic {
    private final int boardSize = 8;
    private Disc[][] discs;
    private Player player1, player2;
    private boolean isFirstPlayerTurn;
    private Stack<Move> moveHistory;

    public GameLogic() {
        isFirstPlayerTurn = true;
    }

    @Override
    public boolean locate_disc(Position a, Disc disc) {
        if (discs[a.row()][a.col()] == null) {
            discs[a.row()][a.col()] = disc;
            return true;
        }
        else return false;
    }

    @Override
    public Disc getDiscAtPosition(Position position) {
        return discs[position.row()][position.col()];
    }

    @Override
    public int getBoardSize() {
        return boardSize;
    }

    @Override
    public List<Position> ValidMoves() {
        List<Position> out = new ArrayList<Position>();
        boolean turn = isFirstPlayerTurn();
        boolean aa = false,
        ab = false,
        ac = false,
        ba = false,
        bc = false,
        ca = false,
        cb = false,
        cc = false;
        for (int i = 0; i < discs.length; i++) {
            Disc[] i_reg = discs[i];
            for (int j = 0; j < i_reg.length; j++) {
                if (discs[i][j] != null)
                    continue;
                if (i-1 < 0){
                    Disc[] i_min = discs[i-1];
                    aa = turn;
                    if(j-1 > 0)
                        aa = i_min[j-1].getOwner().isPlayerOne();
                    ab = i_min[j].getOwner().isPlayerOne();
                    ac = turn;
                    if(j+i == discs[i].length)
                        ac = i_min[j+1].getOwner().isPlayerOne();
                }
                ba = turn; 
                if(j-1 > 0)
                    ba = i_reg[j-1].getOwner().isPlayerOne();
                bc = turn;
                if(j+1 == i_reg.length)
                    bc = i_reg[j+1].getOwner().isPlayerOne();
                if (i+1 == discs.length){
                    Disc[] i_max = discs[i+1];
                    ca = turn;
                    if(j-1 > 0)
                        ca = i_max[j-1].getOwner().isPlayerOne();
                    cb = i_max[j].getOwner().isPlayerOne();
                    cc = turn;
                    if(j+1 == i_max.length)
                        cc = i_max[j+1].getOwner().isPlayerOne();
                }
                if (
                    aa != turn || 
                    ab != turn || 
                    ac != turn || 
                    bc != turn || 
                    ba != turn || 
                    ca != turn || 
                    cb != turn || 
                    cc != turn
                    ) {
                    out.add(new Position(i, j));
                }
            }
        }
        return out;
    }

    @Override
    public int countFlips(Position a) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'countFlips'");
    }

    @Override
    public Player getFirstPlayer() {
        return player1;
    }

    @Override
    public Player getSecondPlayer() {
        return player2;
    }

    @Override
    public void setPlayers(Player player1, Player player2) {
        this.player1= player1;
        this.player2= player2;
    }

    @Override
    public boolean isFirstPlayerTurn() {
        boolean out = isFirstPlayerTurn;
        isFirstPlayerTurn= !isFirstPlayerTurn;
        return out;
    }

    @Override
    public boolean isGameFinished() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isGameFinished'");
    }

    @Override
    public void reset() {
        discs = new Disc[boardSize][boardSize];
        moveHistory = new Stack<Move>();
    }

    @Override
    public void undoLastMove() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'undoLastMove'");
    }
    
}

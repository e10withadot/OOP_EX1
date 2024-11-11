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
        // OUTPUT
        List<Position> out = new ArrayList<Position>();
        boolean turn = isFirstPlayerTurn();
        int directions[][] = {{-1, -1}, {-1, 0}, {0, -1}, {1, 0}, {0, 1}, {1, 1}, {1, -1}, {-1, 1}};
        // rows
        for (int i = 0; i < discs.length; i++) {
            // columns
            for (int j = 0; j < discs[i].length; j++) {
                // if tile is empty
                if (discs[i][j] != null)
                    continue;
                // if move is valid
                boolean valid = false;
                for (int[] dir : directions) {
                    int k = i + dir[0];
                    int l = j + dir[1];
                    // if tile is in bounds
                    boolean ifInBounds = k >= 0 && k < discs.length && l >= 0 && l < discs.length;
                    // and if disc belongs to opponent
                    if(ifInBounds && discs[k][l].getOwner().isPlayerOne() != turn){
                        while(ifInBounds){
                            if (discs[k][l] == null)
                                break;
                            // continue moving
                            k += dir[0];
                            l += dir[1];
                            // if tile is yours
                            if(discs[k][l].getOwner().isPlayerOne() == turn){
                                valid= true;
                                break;
                            }
                        }
                    }
                }
                // final check
                if (valid) {
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

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameLogic implements PlayableLogic {
    private final int boardSize = 8;
    private Disc[][] discs;
    private Player player1, player2;
    private boolean isFirstPlayerTurn;
    private Stack<Move> moveLog;

    @Override
    public boolean locate_disc(Position a, Disc disc) {
        if (discs[a.row()][a.col()] == null && ValidMoves().contains(a)) {
            discs[a.row()][a.col()] = disc;
            countFlips(a);
            moveLog.push(new Move(a, disc));
            isFirstPlayerTurn= !isFirstPlayerTurn;
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
        // rows
        for (int i = 0; i < discs.length; i++) {
            // columns
            for (int j = 0; j < discs[i].length; j++) {
                Position current = new Position(i, j);
                // if tile is not empty
                if (getDiscAtPosition(current) != null)
                    continue;
                if (findSandwiches(current) != null)
                    out.add(current);
            }
        }
        return out;
    }

    /**
     * Checks if the inputted Position is in-bounds.
     * @param a Position object
     * @return True if in-bounds, false otherwise.
    */
    private boolean isInBounds(Position a) {
        return a.row() >= 0 && a.row() < discs.length && a.col() >= 0 && a.col() < discs[0].length;
    }

    /**
     * Checks if the disc in the position belongs to the opposing player.
     * @param a Position object
     * @return True if belongs to enemy, false otherwise.
     */
    private boolean isDiscEnemy(Position a){
        boolean turn = isFirstPlayerTurn();
        // owner check
        return getDiscAtPosition(a).getOwner().isPlayerOne() != turn;
    }

    /**
     * Returns a list of all discs between every sandwich in proximity to the position a.
     * @param a A position with neighboring sandwiches.
     * @return a list of all discs in between sandwiches.
     */
    private List<Position> findSandwiches(Position a) {
        List<Position> out = new ArrayList<>();
        int directions[][] = {{-1, -1}, {-1, 0}, {0, -1}, {1, 0}, {0, 1}, {1, 1}, {1, -1}, {-1, 1}};
        for (int[] dir : directions) {
            Position current = new Position(a.row() + dir[0], a.col() + dir[1]);
            // if enemy disc and in bounds
            if(isInBounds(current) && getDiscAtPosition(current) != null && isDiscEnemy(current)){
                // create temporary list
                List<Position> temp = new ArrayList<>();
                while(isInBounds(current) && getDiscAtPosition(current) != null && isDiscEnemy(current)) {
                    temp.add(current);
                    // continue moving
                    current= new Position(current.row()+dir[0], current.col()+dir[1]);
                }
                // if not enemy disc and in bounds
                if(isInBounds(current) && getDiscAtPosition(current) != null && !isDiscEnemy(current))
                    out.addAll(temp);
            }
        }
        return out;
    }

    /**
     * Flips a disc at the inputted position.
     * @param a Position object
     */
    private void flipDiscAtPosition(Position a) {
        int x = a.row();
        int y = a.col();
        if(discs[x][y].getOwner() == player1)
            discs[x][y].setOwner(player2);
        else discs[x][y].setOwner(player1);
    }

    @Override
    public int countFlips(Position a) {
        Disc disc= getDiscAtPosition(a);
        int flipCount = 0;
        if(disc.getType() == "💣") {

        }
        else {
            List<Position> sandwiches = findSandwiches(a);
            for (Position flip : sandwiches) {
                flipDiscAtPosition(flip);
                flipCount++;
            }
        }
        return flipCount;
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
        return isFirstPlayerTurn;
    }

    @Override
    public boolean isGameFinished() {
        boolean a = ValidMoves() == null;
        isFirstPlayerTurn= !isFirstPlayerTurn;
        boolean b = ValidMoves() == null;
        if(a && b) return true;
        isFirstPlayerTurn= !isFirstPlayerTurn;
        return false;
    }

    @Override
    public void reset() {
        // set board
        discs = new Disc[boardSize][boardSize];
        int i = boardSize/2-1;
        // starting discs
        discs[i][i] = new SimpleDisc(player1);
        discs[i+1][i+1] = new SimpleDisc(player1);
        discs[i+1][i] = new SimpleDisc(player2);
        discs[i][i+1] = new SimpleDisc(player2);
        // reset move log
        moveLog = new Stack<Move>();
        // initiate first turn
        isFirstPlayerTurn = true;
    }

    @Override
    public void undoLastMove() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'undoLastMove'");
    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * GameLogic defines the rules of the Reversi game.
 */
public class GameLogic implements PlayableLogic {
    private final int boardSize = 8;
    private Disc[][] board;
    private Player player1, player2;
    private boolean isFirstPlayerTurn;
    private Stack<Move> history;
    private Move current_move;
    private final int directions[][] = {{-1, -1}, {-1, 0}, {0, -1}, {1, 0}, {0, 1}, {1, 1}, {1, -1}, {-1, 1}};

    public GameLogic() {
        board= new Disc[boardSize][boardSize];
    }

    @Override
    public boolean locate_disc(Position a, Disc disc) {
        if (board[a.row()][a.col()] == null && ValidMoves().contains(a)) {
            // reduce special discs
            boolean isNotZero= disc.reduce();
            if(!isNotZero)
                return false;
            // new current move
            current_move= new Move(a, disc);
            // save new disc
            board[a.row()][a.col()] = disc;
            // player number
            int number;
            if(isFirstPlayerTurn())
                number= 1;
            else
                number= 2;
            // print results
            System.out.println("Player "+number+" placed a "+disc.getType()+" in ("+a.row()+","+a.col()+")");
            // flip necessary discs
            flip(a);
            // back up last move
            history.push(current_move);
            // swap players
            isFirstPlayerTurn= !isFirstPlayerTurn;
            return true;
        }
        else return false;
    }

    @Override
    public Disc getDiscAtPosition(Position position) {
        return board[position.row()][position.col()];
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
        for (int row = 0; row < board.length; row++) {
            // columns
            for (int col = 0; col < board[row].length; col++) {
                Position current = new Position(row, col);
                // if tile is empty
                if (getDiscAtPosition(current) == null){
                    List<Position> sandwiches = findSandwiches(current);
                    if (!sandwiches.isEmpty())
                        out.add(current);
                }
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
        return a.row() >= 0 && a.row() < board.length && a.col() >= 0 && a.col() < board[0].length;
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
     * @return a list of all flippable discs in between sandwiches.
     */
    private List<Position> findSandwiches(Position a) {
        List<Position> out = new ArrayList<>();
        for (int[] dir : directions) {
            Position current = new Position(a.row() + dir[0], a.col() + dir[1]);
            // create temporary list
            List<Position> temp = new ArrayList<>();
            // if enemy disc and in bounds
            while(isInBounds(current) && getDiscAtPosition(current) != null && isDiscEnemy(current)) {
                // if not unflippable
                if(!getDiscAtPosition(current).getType().equals("⭕"))
                    temp.add(current);
                // continue moving
                current= new Position(current.row()+dir[0], current.col()+dir[1]);
            }
            // if not enemy disc and in bounds
            if(isInBounds(current) && getDiscAtPosition(current) != null && !isDiscEnemy(current))
                out.addAll(temp);
        }
        return out;
    }

    /**
     * Flips disc at inputted Position. Returns true if flipped.
     * @param a Position object
     * @return true if flipped, false otherwise.
     */
    private boolean flipDiscAtPosition(Position a, boolean unflip) {
        int x = a.row();
        int y = a.col();
        Disc disc = getDiscAtPosition(a);
        // null/friendly-fire check
        if(disc == null || current_move.disc().getOwner().isPlayerOne() == disc.getOwner().isPlayerOne())
            return false;
        String type = disc.getType();
        // don't flip
        if(type.equals("⭕"))
            return false;
        // flip
        int number;
        if(disc.getOwner() == player1){
            board[x][y].setOwner(player2);
            number=1;
        }
        else {
            board[x][y].setOwner(player1);
            number=2;
        }
        if(unflip)
            // print results
            System.out.println("\tUndo: flipping back "+type+" in ("+x+","+y+")");
        else {
            // add to current move
            current_move.addFlip(a);
            // print results
            System.out.println("Player "+number+" flipped the "+type+" in ("+x+","+y+")");
            // if bomb, explodes
            if(type.equals("💣"))
                explode(a);
        }
        return true;
    }

    /**
     * Creates a chain reaction when a bomb disc explodes.
     * @param a Position object of the bomb disc.
     * @return count of discs flipped.
     */
    private void explode(Position a) {
        int row = a.row(), col = a.col();
        for (int[] dir : directions) {
            // current position
            Position current = new Position(row + dir[0], col + dir[1]);
            if(isInBounds(current)) {
                flipDiscAtPosition(current, false);
            }
        }
    }

    @Override
    public int countFlips(Position a) {
        List<Position> sandwiches = findSandwiches(a);
        return sandwiches.size();
    }

    /**
     * Flip discs according to Disc at Position.
     * @param a Disc Position object
     */
    private void flip(Position a) {
        List<Position> sandwiches = findSandwiches(a);
        for (Position flip : sandwiches) {
            flipDiscAtPosition(flip, false);
        }
    }

    /**
     * Unflips selected discs.
     * @param select List of Position objects
     */
    private void unflip(List<Position> select) {
        for (int i=0; i<select.size(); i++) {
            flipDiscAtPosition(select.get(i), true);
        }
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

    /**
     * Counts every disc on the board.
     * @return int array with the disc count, starting with player 1.
     */
    private int[] countAllDiscs() {
        int counts[] = {0,0};
        for (Disc[] rows : board) {
            for (Disc disc : rows) {
                // null check
                if (disc == null) continue;
                Player owner= disc.getOwner();
                if(owner.equals(player1))
                    counts[0]++;
                else counts[1]++;
            }
        }
        return counts;
    }

    @Override
    public boolean isGameFinished() {
        boolean a = ValidMoves().isEmpty();
        isFirstPlayerTurn= !isFirstPlayerTurn;
        boolean b = ValidMoves().isEmpty();
        isFirstPlayerTurn= !isFirstPlayerTurn;
        if(a && b) {
            int finalCount[] = countAllDiscs();
            if(finalCount[0] > finalCount[1]) {
                player1.addWin();
                System.out.println("Player 1 wins with "+finalCount[0]+" discs! Player 2 had "+finalCount[1]+" discs.");
            }
            else if(finalCount[0] < finalCount[1]) {
                player2.addWin();
                System.out.println("Player 2 wins with "+finalCount[1]+" discs! Player 1 had "+finalCount[0]+" discs.");
            }
            else
                System.out.println("The game is a tie! Both players have "+finalCount[0]+" discs.");
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        // set board
        board = new Disc[boardSize][boardSize];
        int i = boardSize/2-1;
        // starting discs
        board[i][i] = new SimpleDisc(player1);
        board[i+1][i+1] = new SimpleDisc(player1);
        board[i+1][i] = new SimpleDisc(player2);
        board[i][i+1] = new SimpleDisc(player2);
        // reset logs
        history = new Stack<Move>();
        // initiate first turn
        isFirstPlayerTurn = true;
    }

    @Override
    public void undoLastMove() {
        // print initial message
        System.out.println("Undoing last move:");
        // human check
        if (!(player1.isHuman() && player2.isHuman())) {
            System.out.println("\tAI Player found! Can't undo!");
            return;
        }
        // empty check
        if (history.empty()) {
            System.out.println("\tNo previous move available to undo.");
            return;
        }
        // get previous move
        Move move= history.pop();
        Disc disc= move.disc();
        Position pos= move.position();
        // print removal message
        System.out.println("\tUndo: removing "+disc.getType()+" in ("+pos.row()+","+pos.col()+")");
        // unflip flipped discs
        unflip(move.flips());
        // remove disc
        board[pos.row()][pos.col()] = null;
        // restore special discs
        Player owner= disc.getOwner();
        if(disc.getType().equals("⭕")) {
            if(owner.isPlayerOne()) player1.number_of_unflippedable++;
            else player2.number_of_unflippedable++;
        }
        else if (disc.getType().equals("💣")) {
            if(owner.isPlayerOne()) player1.number_of_bombs++;
            else player2.number_of_bombs++;
        }
        // swap players
        isFirstPlayerTurn= !isFirstPlayerTurn;
    }
}
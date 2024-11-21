import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        for (int i = 0; i < 2; i++) {
            // rows
            for (int row = 0; row < board.length; row++) {
                // columns
                for (int col = 0; col < board[row].length; col++) {
                    Position current = new Position(row, col);
                    // if tile is empty
                    if (getDiscAtPosition(current) == null){
                        List<Position> sandwiches = findFlips(current);
                        // if no valid moves
                        if (!sandwiches.isEmpty())
                            out.add(current);
                    }
                }
            }
            // switch to other player if no valid moves
            if(out.isEmpty())
                isFirstPlayerTurn= !isFirstPlayerTurn;
            else
                break;
        }
        return out;
    }

    /**
     * Check if the position is in bounds and has a disc.
     * @param a Position object
     * @return true if Disc exists, false otherwise.
     */
    private boolean discExists(Position a){
        return
            // is in bounds
            a.row() >= 0 && a.row() < board.length && a.col() >= 0 && a.col() < board[0].length
            // is not empty
            && getDiscAtPosition(a) != null;
    }

    /**
     * Checks if Disc type matches emoji.
     * @param pos Disc's position
     * @param type String containing "⬤", "⭕", or "💣".
     * @return true if type matches, false otherwise.
     */
    private boolean discTypeIs(Position pos, String type){
        return getDiscAtPosition(pos).getType().equals(type);
    }

    /**
     * Checks if the disc in the position belongs to the opposing player.
     * @param a Position object
     * @return true if belongs to enemy, false otherwise.
     */
    private boolean isDiscEnemy(Position a){
        // if disc owner is not current player
        return getDiscAtPosition(a).getOwner().isPlayerOne() != isFirstPlayerTurn();
    }

    /**
     * Returns a list of all positions flipped due to position a.
     * @param a Position object
     * @return a list of all positions affected.
     */
    private List<Position> findFlips(Position a) {
        List<Position> out = new ArrayList<>();
        List<Position> explosions = new ArrayList<>();
        for (int[] dir : directions) {
            Position current = new Position(a.row() + dir[0], a.col() + dir[1]);
            List<Position> positions= new ArrayList<>();
            while(discExists(current) && isDiscEnemy(current)){
                // add position
                positions.add(current);
                // continue moving
                current= new Position(current.row()+dir[0], current.col()+dir[1]);
            }
            // if in bounds
            if(discExists(current)) {
                for (Position position : positions) {
                    // if not unflippable
                    if(!discTypeIs(position, "⭕")) {
                        out.add(position);
                        // if bomb
                        if(discTypeIs(position, "💣")){
                            explosions.add(position);
                        }
                    }
                }
            }
        }
        // positions affected by blast
        for (Position explosion : explosions) {
            List<Position> radius= explode(explosion, new Stack<Position>());
            for (Position blast : radius) {
                if(!out.contains(blast))
                    out.add(blast);
            }
        }
        return out;
    }

    /**
     * Flips disc at inputted Position. Prints result.
     * @param a Position object
     */
    private void flipDiscAtPosition(Position a, boolean unflip) {
        int x = a.row();
        int y = a.col();
        // flip
        int number;
        if(isFirstPlayerTurn()){
            board[x][y].setOwner(player1);
            number=1;
        }
        else {
            board[x][y].setOwner(player2);
            number=2;
        }
        String type = getDiscAtPosition(a).getType();
        if(unflip)
            // print results
            System.out.println("\tUndo: flipping back "+type+" in ("+x+","+y+")");
        else {
            // add to current move
            current_move.addFlip(a);
            // print results
            System.out.println("Player "+number+" flipped the "+type+" in ("+x+","+y+")");
        }
    }

    /**
     * Creates a chain reaction when a bomb disc explodes.
     * @param a Position object of the bomb disc.
     * @return count of discs flipped.
     */
    private List<Position> explode(Position a, Stack<Position> history) {
        // blast radius
        List<Position> radius= new ArrayList<>();
        // recursion check
        if (history.contains(a))
            return radius;
        // add to history
        history.push(a);
        for (int[] dir : directions) {
            // current position
            Position current = new Position(a.row() + dir[0], a.col() + dir[1]);
            if(discExists(current) && isDiscEnemy(current)) {
                radius.add(current);
                // chain reaction
                if(discTypeIs(current, "💣") && !history.contains(a))
                    radius.addAll(explode(current, history));
            }
        }
        return radius;
    }

    @Override
    public int countFlips(Position a) {
        List<Position> sandwiches = findFlips(a);
        return sandwiches.size();
    }

    /**
     * Flip discs according to Disc at Position.
     * @param a Disc Position object
     */
    private void flip(Position a) {
        List<Position> sandwiches = findFlips(a);
        for (Position flip : sandwiches) {
            flipDiscAtPosition(flip, false);
        }
    }

    /**
     * Unflips selected discs.
     * @param select List of Position objects
     */
    private void unflip(List<Position> select) {
        for (Position p : select) {
            flipDiscAtPosition(p, true);
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
        if(ValidMoves().isEmpty()) {
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
        // restore special discs
        disc.restore();
        // remove disc
        board[pos.row()][pos.col()] = null;
        // swap players
        isFirstPlayerTurn= !isFirstPlayerTurn;
    }
}
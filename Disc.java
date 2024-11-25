/**
 * The Disc interface defines the characteristics of a game in a chess-like game.
 * Implementing classes should provide information about the player who owns the Disc.
 */
public abstract class Disc {
    protected Player owner;

    public Disc(Player owner) {
        this.owner = owner;
    }

    /**
     * Get the player who owns the Disc.
     *
     * @return The player who is the owner of this game disc.
     */
    public Player getOwner(){
        return this.owner;
    }

    /**
     * Set the player who owns the Disc.
     *
     */
    public void setOwner(Player newOwner){
        this.owner= newOwner;
    }

    /**
     * Get the type of the disc.
     * use the:
     *          "⬤",         "⭕"                "💣"
     *      Simple Disc | Unflippedable Disc | Bomb Disc |
     * respectively.
     */
    public String getType(){
        return null;
    }

    /**
     * Reduces owner's amount of the disc. 
     * @return true if not zero, false otherwise.
     */
    public boolean reduce() {
        return true;
    }

    /**
     * Adds one back to owner's amount of the disc.
     */
    public void restore() {

    }

    /**
     * Copies the Disc object.
     * @return copied Disc object.
     */
    public Disc copy() {
        return null;
    }
}
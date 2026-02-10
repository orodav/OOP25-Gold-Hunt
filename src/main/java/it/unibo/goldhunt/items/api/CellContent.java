package it.unibo.goldhunt.items.api;
//luca

import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Represents the content and effects of an item in game.
 * <p>
 * A {@code CellContent} can apply an effect when activated
 * and provides a short textual representation.
*/

public interface CellContent{

    /**
     * Applies the effect to the player.
     *
     * @param playerop the player
     * @return the updated PlayerOperations if applied or null.
     */
    public PlayerOperations applyEffect(PlayerOperations playerop);

    /**
     * Returns a short string representation of the class.
     * 
     * @return a short string representing the content
     */
    public String shortString();

    /**
     * Indicates that this content is a trap.
     * <p>
     * The default implementation returns {@code false};
     * trap should override this method.
     *
     * @return {@code true} if the content is a trap,
     *         {@code false} otherwise
     */
    default boolean isTrap(){
        return false;
    }


}

package it.unibo.goldhunt.items.api;
//luca

/**
 * Represents the content and effects of an item.
 * <p>
 * A {@code CellContent} can apply an effect when activated
 * and provides a short textual representation.
*/

public interface CellContent{

    /**
     * Applies the effect associated with this cell content.
     *
     * @return {@code true} if the effect was successfully applied,
     *         {@code false} otherwise
     */
    public boolean applyEffect();

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

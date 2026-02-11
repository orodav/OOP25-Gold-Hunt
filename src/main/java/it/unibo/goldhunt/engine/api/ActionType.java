package it.unibo.goldhunt.engine.api;

/**
 * Represents the type of action that can be performed by the player during the game.
 * 
 * <p>
 * An {@code ActionType} identifies the nature of the requested operation
 * and is typically included in an {@link ActionResult} to describe which
 * action has been executed.
 */
public enum ActionType {
    /**
     * Represents a movement action.
     * 
     * <p>
     * Used when the player attempts to move from the current position
     * to another valid position.
     */
    MOVE,
    /**
     * Represents a reveal action.
     * 
     * <p>
     * Used when the player attempts to uncover the content of a cell.
     */
    REVEAL,
    /**
     * Represents a flag action.
     * 
     * <p>
     * Used when the player marks or unmarks a cell to signal a trap
     */
    FLAG
}

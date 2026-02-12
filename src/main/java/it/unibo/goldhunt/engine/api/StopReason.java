package it.unibo.goldhunt.engine.api;

/**
 * Represents the reason why a movement action stopped.
 * 
 * <p>
 * A {@link StopReason} provides contextual information about
 * the outcome of a move attempt, independently from the 
 * {@link ActionEffect} produced.
 */
public enum StopReason {

    /**
     * The player successfully reached the target cell.
     */
    REACHED_CELL,

    /**
     * The player was already located on the target cell.
     */
    ALREADY_THERE,

    /**
     * The movement was interrupted due to a blocking condition.
     */
    BLOCKED,

    /**
     * No valid path could be computed between the starting and target positions.
     */
    NO_AVAILABLE_PATH,

    /**
     * The movement stopped because the player reached a warning cell,
     * that requires immediate interruption.
     */
    ON_WARNING,

    /**
     * No specific stop condition applies.
     */
    NONE
}

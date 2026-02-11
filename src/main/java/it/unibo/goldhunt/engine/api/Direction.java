package it.unibo.goldhunt.engine.api;

/**
 * Represents the possible directions used for player movement
 * on board navigation.
 * 
 * <p>
 * A {@code Direction} can be used to compute adjacent positions
 * starting from a given {@link Position}. It includes both the
 * four cardinal directions and the four diagonal ones,
 * allowing full 8-neighborhood traversal of the board.
 */
public enum Direction {
    /**
     * Upward direction.
     */
    UP,
    /**
     * Downward direction.
     */
    DOWN,
    /**
     * Left direction.
     */
    LEFT,
    /**
     * Right direction.
     */
    RIGHT,
    /**
     * Up-left diagonal direction.
     */
    UP_LEFT,
    /**
     * Up-right diagonal direction.
     */
    UP_RIGHT,
    /**
     * Down-left diagonal direction.
     */
    DOWN_LEFT,
    /**
     * Down-right diagonal direction.
     */
    DOWN_RIGHT
}

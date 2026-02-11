package it.unibo.goldhunt.engine.api;

/**
 * Represents an immutable coordinate on the game board.
 * 
 * A {@code Position} identifies a cell using two integer
 * coordinates. The semantic meaning of {@code x} and {@code y}
 * depends on the board implementation.
 * 
 * This record is immutable and can be safely used as a key
 * in collections.
 * 
 * @param x the horizontal coordinate
 * @param y the vertical coordinate
 */
public record Position(int x, int y) {
}

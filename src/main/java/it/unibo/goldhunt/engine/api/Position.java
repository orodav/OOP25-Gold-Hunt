package it.unibo.goldhunt.engine.api;

/**
 * Represents an immutable two-dimensional coordinate on the game board.
 * 
 * A {@code Position} identifies a cell on the board using zero-based
 * Cartesian coordinates.
 * 
 * @param x the horizontal coordinate
 * @param y the vertical coordinate
 */
public record Position(int x, int y) {
}

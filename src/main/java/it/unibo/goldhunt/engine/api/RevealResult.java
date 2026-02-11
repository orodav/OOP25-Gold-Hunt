package it.unibo.goldhunt.engine.api;

/**
 * Represents the result of a reveal operation performed on the board.
 * 
 * A {@code RevealResult} encapsulates the outcome of revealing a cell.
 * This record is typically used internally by the engine or the board
 * to communicate the effects of a reveal action before wrapping them.
 * 
 * @param changed {@code true} if the reveal operation modified the board state
 *                {@code false} otherwise
 * @param effect the {@link ActionEffect} produced by the reveal operation
 * @param newLevelState the resulting {@link LevelState} afer the reveal
 */
public record RevealResult(
    boolean changed,
    ActionEffect effect,
    LevelState newLevelState
) { }

package it.unibo.goldhunt.engine.api;

/**
 * Represents the result of executing a game action.
 * 
 * An {@code ActionResult} encapsulates all the information needed to
 * describe the outcome of an action performed by the player.
 * This record is immutable and is typically returned by the engine
 * after operations such as move or reveal.
 * 
 * @param type the {@link AcitonType} representing the executed action
 * @param reason the {@link StopReason} explaining why the action stopped
 * @param levelState the resulting {@link LevelState} after the action
 * @param effect the {@link ActionEffect} produced by the action
 */
public record ActionResult(
    ActionType type,
    StopReason reason,
    LevelState levelState,
    ActionEffect effect
) { }

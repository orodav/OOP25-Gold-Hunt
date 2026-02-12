package it.unibo.goldhunt.view.viewstate;

/**
 * Immutable snapshot containing the information displayed in the game's HUD section.
 * 
 * @param levelNumber the current level number
 * @param lives the current player lives
 * @param gold the current player gold
 */
public record HudViewState(
    int levelNumber,
    int lives,
    int gold
) { }

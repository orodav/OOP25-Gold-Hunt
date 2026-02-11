package it.unibo.goldhunt.view.viewstate;

/**
 * Immutable snapshot containing the information displayed in the game's HUD section.
 */
public record HudViewState(
    int levelNumber,
    int lives,
    int gold
) { }

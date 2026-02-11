package it.unibo.goldhunt.view.viewmodel;

import it.unibo.goldhunt.configuration.api.Difficulty;

/**
 * Immutable snapshot containing the information displayed in the game's HUD section.
 */
public record HudViewState(
    Difficulty difficulty,
    int levelNumber,
    int lives,
    int gold
) { }

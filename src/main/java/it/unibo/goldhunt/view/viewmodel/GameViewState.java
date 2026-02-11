package it.unibo.goldhunt.view.viewmodel;

import java.util.List;
import java.util.Optional;

import it.unibo.goldhunt.engine.api.GameMode;
import it.unibo.goldhunt.engine.api.LevelState;
import it.unibo.goldhunt.engine.api.Position;

/**
 * Immutable snapshot representing the complete UI state of the game at a given moment.
 */
public record GameViewState(
    int boardSize,
    List<CellViewState> cells,
    Position playerPos,
    HudViewState hud,
    Optional<ShopViewState> shop,
    String message,
    GameMode mode,
    LevelState levelState
) { }

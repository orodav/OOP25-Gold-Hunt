package it.unibo.goldhunt.view.viewstate;

import java.util.List;
import java.util.Optional;

import it.unibo.goldhunt.engine.api.LevelState;
import it.unibo.goldhunt.engine.api.Position;

/**
 * Immutable snapshot representing the complete UI state of the game at a given moment.
 * 
 * <p>
 * {@code cells} always contains exactly {@code boardSize * boardSize} elements.
 * The cells are ordered in row-major order
 *      (0,0), (0,1), ..., (0, boardSize - 1),
 *      (1,0), (1,1), ..., (1, boardSize - 1),
 *      ...
 *      (boardSize-1, boardSize-1).
 * 
 * <p>
 * This locks the mapping of the list index to grid coordinates.
 * 
 * @param boardSize the board size
 * @param cells the current cell view states
 * @param playerPos the current player position
 * @param hud the HUD snapshot
 * @param inventory the inventory snapshot
 * @param shop the shop snapshot if available
 * @param message an optional UI message
 * @param screen the current game mode
 * @param levelState the current level state
 */
public record GameViewState(
    int boardSize,
    List<CellViewState> cells,
    Position playerPos,
    HudViewState hud,
    InventoryViewState inventory,
    Optional<ShopViewState> shop,
    Optional<String> message,
    ScreenType screen,
    LevelState levelState
) { }

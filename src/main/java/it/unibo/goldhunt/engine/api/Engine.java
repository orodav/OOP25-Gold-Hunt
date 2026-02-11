package it.unibo.goldhunt.engine.api;

import it.unibo.goldhunt.player.api.Player;

/**
 * Represents the core component of the game logic.
 * 
 * The {@code Engine} acts as the "brain" of the game: it 
 * coordinates player actions, applies game rules, interacts
 * with the board, and produces {@link ActionResult}s 
 * describing the outcome of each operation.
 * 
 * The engine is responsible for validating actions according to
 * the current {@link Status}, updating the game state, and 
 * ensuring consistency between the player, the board, and the
 * overall level state.
 */
public interface Engine {

    /**
     * Returns the current {@link Player}.
     * 
     * @return the player associated with this engine
     */
    Player player();

    /**
     * Returns the current {@link Status} of the game.
     * 
     * @return the current game status
     */
    Status status();

    /**
     * Attempts to reveal the cell at the given position.
     * 
     * @param p the position to reveal
     * @return an {@link ActionResult} describing the outcome
     * @throws IllegalArgumentException if {@code p} is {@code null}
     */
    ActionResult reveal(Position p);

    /**
     * Toggles the flag state of the cell at the given position.
     * 
     * @param p the position whose flag state is to be toggled
     * @return an {@link ActionResult} describing the outcome
     * @throws IllegalArgumentException if {@code p} is {@code null}
     */
    ActionResult toggleFlag(Position p);

    /**
     * Attempts to move the player to the specified position.
     * 
     * @param newPos the target position
     * @return an {@link ActionResult} describing the outcome
     * @throws IllegalArgumentException if {@code newPos} is {@code null}
     */
    ActionResult move(Position newPos);
}

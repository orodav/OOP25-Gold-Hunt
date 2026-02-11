package it.unibo.goldhunt.root;

import java.util.Objects;
import java.util.Optional;

import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.configuration.api.Level;
import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.engine.api.EngineWithState;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.player.api.Player;
import it.unibo.goldhunt.shop.api.Shop;

/**
 * Represents a single game session.
 * 
 * <p>
 * A {@code GameSession} acts as a high-level controller that coordinate
 * the interaction between configuration data.
 * 
 * <p>
 * It provides a simplified facade for accessing the current game state
 * and performing player actions.
 */
public final class GameSession {

    private final Difficulty difficulty;
    private final Level level;
    private final EngineWithState engine;

    /**
     * Creates a new game session with the specified configuration and engine.
     * 
     * @param difficulty the selected difficulty
     * @param level the level configurations
     * @param engine the engine managing game
     * @throws NullPointerException if any parameter is {@code null}
     */
    public GameSession(
        final Difficulty difficulty,
        final Level level,
        final EngineWithState engine
    ) {
        this.difficulty = Objects.requireNonNull(difficulty, "difficulty can't be null");
        this.level = Objects.requireNonNull(level, "level can't be null");
        this.engine = Objects.requireNonNull(engine, "engine can't be null");
    }

    /**
     * Returns the selected difficulty.
     * 
     * @return the difficulty of this session
     */
    public Difficulty difficulty() {
        return this.difficulty;
    }

    /**
     * Returns the level configuration associated with this session.
     * 
     * @return the level
     */
    public Level level() {
        return this.level;
    }

    /**
     * Return the underlying engine instance.
     * 
     * @return the engine managing the game
     */
    public EngineWithState engine() {
        return this.engine;
    }

    /**
     * Returns the shop associated with the current game state.
     * 
     * @return an {@code Optional} containing the shop, if present
     */
    public Optional<Shop> shop() {
        return this.engine.state().shop();
    }

    /**
     * Returns the current player snapshot.
     * 
     * @return the player
     */
    public Player player() {
        return this.engine.state().player();
    }

    /**
     * return the current game status.
     * 
     * @return the status
     */
    public Status status() {
        return this.engine.state().status();
    }

    /**
     * Delegates a movement action to the engine.
     * 
     * @param p the target position
     * @return the result of the movement action
     */
    public ActionResult move(final Position p) {
        return this.engine.move(p);
    }

    /**
     * Delegates a reveal action to the engine.
     * 
     * @param p the target position
     * @return the result of the reveal action
     */
    public ActionResult reveal(final Position p) {
        return this.engine.reveal(p);
    }

    /**
     * Delegates a flag toggle action to the engine.
     * 
     * @param p the target position
     * @return the result of the flag toggle action
     */
    public ActionResult toggleFlag(final Position p) {
        return this.engine.toggleFlag(p);
    }
}

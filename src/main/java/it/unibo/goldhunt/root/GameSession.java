package it.unibo.goldhunt.root;

import java.util.Objects;
import java.util.Optional;

import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.configuration.api.Level;
import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.engine.api.EngineWithState;
import it.unibo.goldhunt.engine.api.GameMode;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.items.api.ItemFactory;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.items.api.KindOfItem;
import it.unibo.goldhunt.player.api.PlayerOperations;
import it.unibo.goldhunt.shop.api.Shop;
import it.unibo.goldhunt.shop.api.ShopActionResult;

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
    private EngineWithState engine;
    private Optional<EngineWithState.EngineWithShopActions> shopEngine;
    private final ItemFactory itemFactory;

    /**
     * Creates a new game session with the specified configuration and engine.
     * 
     * @param difficulty the selected difficulty
     * @param level the level configurations
     * @param engine the engine managing game
     * @param shopEngine an optional reference to the engine with shop capabilities
     * @param itemFactory the factory used to generate usable items
     * @throws NullPointerException if any parameter is {@code null}
     */
    public GameSession(
        final Difficulty difficulty,
        final Level level,
        final EngineWithState engine,
        final Optional<EngineWithState.EngineWithShopActions> shopEngine,
        final ItemFactory itemFactory
    ) {
        this.difficulty = Objects.requireNonNull(difficulty, "difficulty can't be null");
        this.level = Objects.requireNonNull(level, "level can't be null");
        this.engine = Objects.requireNonNull(engine, "engine can't be null");
        this.shopEngine = Objects.requireNonNull(shopEngine, "shopEngine can't be null");
        this.itemFactory = Objects.requireNonNull(itemFactory, "itemFactory can't be null");

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
    public PlayerOperations player() {
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

    /**
     * Attempts to buy one unit of the specified item from the current shop.
     * 
     * @param type the item type to buy
     * @return a {@link ShopActionResult} describing the outcome
     * @throws NullPointerException if {@code type} is {@code null}
     * @throws IllegalStateException if the current engine does not support shop actions
     */
    public ShopActionResult buy(final ItemTypes type) {
        Objects.requireNonNull(type, "type can't be null");
        final EngineWithState.EngineWithShopActions shopEn = this.shopEngine
                .orElseThrow(
                    () -> new IllegalStateException("Shop actions not available in this session")
                );
        final ShopActionResult shopAR = shopEn.buy(type);
        this.engine = shopEn;
        return shopAR;
    }

    /**
     * Leaves the current shop.
     * Updates the session state so that {@link #shop()} becomes empty.
     *
     * @throws IllegalStateException if shop actions are not available in this session
     */
    public void leaveShop() {
        final EngineWithState.EngineWithShopActions shopEn = this.shopEngine
            .orElseThrow(
                () -> new IllegalStateException("Shop actions not available in this session"));
        shopEn.leaveShop();
        this.engine = shopEn;
        this.shopEngine = Optional.of(shopEn);
    }

    /**
     * Uses one unit of the specified item form the player's inventory.
     * 
     * @param type the type of item to use
     * @throws NullPointerException if {@code type} is {@code null}
     * @throws IllegalStateException if the game is not in level mode,
     *                               if shop actions are unavailable,
     *                               or if the item effect returns {@code null}
     * @throws IllegalArgumentException if the player does not have at least
     *                                  one unit of the specified item
     */
    public void useItem(final ItemTypes type) {
        Objects.requireNonNull(type, "type can't be null");

        if (this.status().gameMode() != GameMode.LEVEL) {
            throw new IllegalStateException("can't use items outside level");
        }
        if (type == KindOfItem.SHIELD || type == KindOfItem.LUCKYCLOVER) {
            return;
        }

        final PlayerOperations currentPlayer = this.player();
        if (!currentPlayer.inventory().hasAtLeast(type, 1)) {
            throw new IllegalArgumentException("not enough item in inventory");
        }

        final var usableItem = this.itemFactory.generateItem(
            type.shortString(),
            this.level.getBoard(),
            currentPlayer,
            currentPlayer.inventory()
        );

        final PlayerOperations effectedPlayer = usableItem.applyEffect(currentPlayer);
        if (effectedPlayer == null) {
            throw new IllegalStateException("item applyEffect returned null: " + type.getItem());
        }
        final PlayerOperations updatedPlayer = effectedPlayer.useItem(type, 1);
        final EngineWithState updatedEngine = this.engine.withPlayer(updatedPlayer);
        this.engine = updatedEngine;
        if (updatedEngine instanceof EngineWithState.EngineWithShopActions engineShopAction) {
            this.shopEngine = Optional.of(engineShopAction);
        } else {
            this.shopEngine = Optional.empty();
        }
    }

    /**
     * Enters the shop phase for the current session.
     * 
     * @throws IllegalStateException if shop actions are not available
     */
    public void enterShop() {
    final EngineWithState.EngineWithShopActions shopEn = this.shopEngine
        .orElseThrow(() -> new IllegalStateException("Shop actions not available"));
    shopEn.enterShop();
    this.engine = shopEn;
    this.shopEngine = Optional.of(shopEn);
    consumeLuckyCloverIfPresent();
    }

    private void consumeLuckyCloverIfPresent() {
        final PlayerOperations p = this.player();
        if (p.inventory().hasAtLeast(KindOfItem.LUCKYCLOVER, 1)) {
            final PlayerOperations updated = p.useItem(KindOfItem.LUCKYCLOVER, 1);
            this.engine = this.engine.withPlayer(updated);
            if (this.engine instanceof EngineWithState.EngineWithShopActions shopEn) {
                this.shopEngine = Optional.of(shopEn);
            } else {
                this.shopEngine = Optional.empty();
            }
        }
    }
}

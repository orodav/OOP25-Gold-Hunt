package it.unibo.goldhunt.root;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import it.unibo.goldhunt.board.api.BoardFactory;
import it.unibo.goldhunt.board.api.CellFactory;
import it.unibo.goldhunt.board.api.RevealStrategy;
import it.unibo.goldhunt.board.impl.BaseCellFactory;
import it.unibo.goldhunt.board.impl.FloodReveal;
import it.unibo.goldhunt.board.impl.SquareBoardFactory;
import it.unibo.goldhunt.configuration.api.BoardGenerator;
import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.configuration.api.Level;
import it.unibo.goldhunt.configuration.api.LevelConfig;
import it.unibo.goldhunt.configuration.api.LevelConfigFactory;
import it.unibo.goldhunt.configuration.impl.BoardGeneratorImpl;
import it.unibo.goldhunt.configuration.impl.LevelImpl;
import it.unibo.goldhunt.configuration.impl.LevelConfigFactoryImpl;
import it.unibo.goldhunt.engine.api.EngineWithState.EngineWithShopActions;
import it.unibo.goldhunt.engine.api.GameMode;
import it.unibo.goldhunt.engine.api.LevelState;
import it.unibo.goldhunt.engine.api.MovementRules;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.engine.impl.EngineImpl;
import it.unibo.goldhunt.engine.impl.MovementRulesImpl;
import it.unibo.goldhunt.engine.impl.StatusImpl;
import it.unibo.goldhunt.items.api.ItemFactory;
import it.unibo.goldhunt.items.api.TrapFactory;
import it.unibo.goldhunt.items.impl.ItemFactoryImpl;
import it.unibo.goldhunt.items.impl.TrapFactoryImpl;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;
import it.unibo.goldhunt.player.impl.InventoryImpl;
import it.unibo.goldhunt.player.impl.PlayerImpl;
import it.unibo.goldhunt.shop.api.ShopFactory;
import it.unibo.goldhunt.shop.api.ShopItem;
import it.unibo.goldhunt.shop.impl.DefaultShopCatalog;
import it.unibo.goldhunt.shop.impl.DefaultShopFactory;

/**
 * Factory responsible for creating fully wired {@link GameSession} instances.
 * 
 * <p>
 * {@code GameFactory} acts as the composition root of the application.
 * 
 * <p>
 * The default constructor provides a ready-to-use configuration,
 * configuration following a simple wiring strategy (KISS principle).
 */
public class GameFactory {

    private static final Position DEFAULT_START = new Position(0, 0);
    private static final int DEFAULT_INITIAL_LIVES = 0;
    private static final int DEFAULT_INITIAL_GOLD = 0;
    private static final int DEFAULT_SHOP_MAX_PURCHASES = 3;
    private final LevelConfigFactory configFactory;
    private final CellFactory cellFactory;
    private final RevealStrategy revealStrategy;
    private final ItemFactory itemFactory;
    private final ShopFactory shopFactory;
    private final List<ShopItem> shopCatalog;
    private final int shopMaxPurchases;

    /**
     * Creates a {@code GameFactory} with default component wiring.
     * 
     * <p>
     * This configuration uses standard implementations.
     */
    public GameFactory() {
        this.configFactory = new LevelConfigFactoryImpl();
        this.cellFactory = new BaseCellFactory();
        this.revealStrategy = new FloodReveal();
        this.itemFactory = new ItemFactoryImpl();
        this.shopFactory = new DefaultShopFactory();
        this.shopCatalog = DefaultShopCatalog.create();
        this.shopMaxPurchases = DEFAULT_SHOP_MAX_PURCHASES;
    }

    /**
     * Creates and initializes a new {@link GameSession} for the specified difficulty.
     * 
     * @param difficulty the selected game difficulty
     * @return a fully initialized {@code GameSession}
     * @throws NullPoiterException if {@code difficulty} is {@code null}
     */
    public GameSession newGame(final Difficulty difficulty) {
        Objects.requireNonNull(difficulty);
        final LevelConfig config = this.configFactory.create(difficulty);
        final Inventory inventory = new InventoryImpl();
        final PlayerOperations player = new PlayerImpl(
            DEFAULT_START, 
            DEFAULT_INITIAL_LIVES, 
            DEFAULT_INITIAL_GOLD, 
            inventory
        );
        final BoardFactory boardFactory = new SquareBoardFactory(this.cellFactory);
        final TrapFactory trapFactory = new TrapFactoryImpl(player);
        final BoardGenerator boardGenerator = new BoardGeneratorImpl(
            boardFactory, 
            trapFactory, 
            this.itemFactory,
            player
        );
        final Level level = new LevelImpl(config, boardGenerator, player);
        level.initBoard();
        level.initPlayerPosition();
        level.initLives();
        final MovementRules rules = new MovementRulesImpl(level.getBoard());
        final Status status = StatusImpl.createStartingState();
        final EngineWithShopActions engine = new EngineImpl(
            level.getPlayer(), 
            status, 
            level.getBoard(), 
            rules, 
            this.revealStrategy, 
            level.getStart(), 
            level.getExit(),
            this.shopFactory,
            this.shopCatalog,
            this.shopMaxPurchases
        );
        return new GameSession(difficulty, level, engine, Optional.of(engine), this.itemFactory);
    }

    /**
     * Initializes a new {@link GameSession} for the specified difficulty.
     * 
     * <p>
     * Keeps trace of gold amount and items in inventory from previous levels.
     * 
     * @param current the current game session providing the player state
     * @param difficulty the selected game difficulty
     * @return a fully initialized {@code GameSession}
     * @throws NullPoiterException if {@code difficulty} is {@code null}
     */
    public GameSession nextLevel(final GameSession current, final Difficulty difficulty) {
        Objects.requireNonNull(current, "current can't be null");
        Objects.requireNonNull(difficulty, "difficulty can't be null");

        final PlayerOperations player = current.player();
        final LevelConfig config = this.configFactory.create(difficulty);
        final BoardFactory boardFactory = new SquareBoardFactory(this.cellFactory);
        final TrapFactory trapFactory = new TrapFactoryImpl(player);
        final BoardGenerator boardGenerator = new BoardGeneratorImpl(
            boardFactory,
            trapFactory,
            this.itemFactory,
            player
        );
        final Level level = new LevelImpl(config, boardGenerator, player);
        level.initBoard();
        level.initPlayerPosition();
        level.initLives();

        final MovementRules rules = new MovementRulesImpl(level.getBoard());
        final Status status = current.status()
            .withGameMode(GameMode.LEVEL)
            .withLevelState(LevelState.PLAYING);
        final EngineWithShopActions engine = new EngineImpl(
            level.getPlayer(),
            status,
            level.getBoard(),
            rules,
            this.revealStrategy,
            level.getStart(),
            level.getExit(),
            this.shopFactory,
            this.shopCatalog,
            this.shopMaxPurchases
        );
        return new GameSession(difficulty, level, engine, Optional.of(engine), this.itemFactory);
    }
}

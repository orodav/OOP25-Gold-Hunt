package it.unibo.goldhunt.root;

import java.util.List;
import java.util.Objects;

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
import it.unibo.goldhunt.engine.api.Engine;
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
import it.unibo.goldhunt.shop.api.Shop;
import it.unibo.goldhunt.shop.api.ShopItem;
import it.unibo.goldhunt.shop.impl.ShopImpl;

public class GameFactory {

    private static final Position DEFAULT_START = new Position(0, 0);
    private static final int DEFAULT_INITIAL_LIVES = 0;
    private static final int DEFAULT_INITIAL_GOLD = 0;
    private static final int DEFAULT_SHOP_MAX_PURCHASES = 3;
    private final LevelConfigFactory configFactory;
    private final CellFactory cellFactory;
    private final RevealStrategy revealStrategy;
    private final ItemFactory itemFactory;
    private final List<ShopItem> shopCatalog;
    private final int shopMaxPurchases;

    /**
     * Default wiring (KISS).
     */
    public GameFactory() {
        this.configFactory = new LevelConfigFactoryImpl();
        this.cellFactory = new BaseCellFactory();
        this.revealStrategy = new FloodReveal();
        this.itemFactory = new ItemFactoryImpl();
        this.shopCatalog = List.of();
        this.shopMaxPurchases = DEFAULT_SHOP_MAX_PURCHASES;
    }

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
            this.itemFactory
        );
        final Level level = new LevelImpl(config, boardGenerator, player);
        level.initBoard();;
        level.initPlayerPosition();
        level.initLives();
        final MovementRules rules = new MovementRulesImpl(level.getBoard());
        final Status status = StatusImpl.createStartingState();
        final Engine engine = new EngineImpl(
            level.getPlayer(), 
            status, 
            level.getBoard(), 
            rules, 
            this.revealStrategy, 
            level.getStart(), 
            level.getExit()
        );
        final Shop shop = new ShopImpl(
            level.getPlayer(), 
            this.shopCatalog, 
            this.shopMaxPurchases
        );
        return new GameSession(difficulty, level, engine, shop);
    }
}

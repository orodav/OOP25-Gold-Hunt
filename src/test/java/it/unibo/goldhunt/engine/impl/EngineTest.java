package it.unibo.goldhunt.engine.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.board.api.RevealStrategy;
import it.unibo.goldhunt.engine.api.ActionEffect;
import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.engine.api.GameMode;
import it.unibo.goldhunt.engine.api.LevelState;
import it.unibo.goldhunt.engine.api.MovementRules;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.items.api.CellContent;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.items.api.KindOfItem;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.Player;
import it.unibo.goldhunt.player.api.PlayerOperations;
import it.unibo.goldhunt.player.impl.InventoryImpl;
import it.unibo.goldhunt.player.impl.PlayerImpl;
import it.unibo.goldhunt.shop.api.Shop;
import it.unibo.goldhunt.shop.api.ShopActionEffect;
import it.unibo.goldhunt.shop.api.ShopActionResult;
import it.unibo.goldhunt.shop.api.ShopFactory;
import it.unibo.goldhunt.shop.api.ShopItem;
import it.unibo.goldhunt.shop.impl.ShopImpl;

/**
 * Testing class for Engine implementation.
 */
class EngineTest {

    private static final int NINE = 9;
    private static final int SEVEN = 7;
    private static final int FIVE = 5;
    private static final int THREE = 3;
    private PlayerOperations player;
    private Status status;
    private TestBoard board;
    private TestRules rules;
    private TestStrategy strategy;
    private Position start;
    private Position exit;
    private ShopFactory shopFactory;
    private List<ShopItem> globalCatalog;
    private int shopLimit;

    private static PlayerOperations makePlayer(final Position p) {
        final Inventory inventory = new InventoryImpl();
        return new PlayerImpl(p, 3, 0, inventory);
    }

    /**
     * Initializes the shared test fixtures used by each test.
     */
    @BeforeEach
    void init() {
        this.status = StatusImpl.createStartingState();
        this.start = new Position(0, 0);
        this.exit = new Position(0, 2);
        this.player = makePlayer(this.start);
        this.rules = new TestRules();
        this.strategy = new TestStrategy();
        final Position p00 = new Position(0, 0);
        final Position p01 = new Position(0, 1);
        final Position p02 = new Position(0, 2);
        this.board = new TestBoard(
            Set.of(p00, p01, p02), 
            Map.of(
                p00, new TestCell(false, false),
                p01, new TestCell(false, false),
                p02, new TestCell(false, false)
            )
        );
        this.shopLimit = 3;
        this.globalCatalog = List.of(
            new ShopItem(StubItem.A, THREE),
            new ShopItem(StubItem.B, FIVE),
            new ShopItem(StubItem.C, SEVEN),
            new ShopItem(StubItem.D, NINE)
        );
        this.shopFactory = (playerOps, catalog, maxPurchases)
                -> new ShopImpl(playerOps, catalog, maxPurchases);
    }

    private EngineImpl newEngine(
        final PlayerOperations playerOps,
        final Status gameStatus,
        final Board gameBoard,
        final MovementRules gameMovementRules,
        final RevealStrategy gameRevealStrategy,
        final Position gameStart,
        final Position gameExit,
        final ShopFactory gameShopFactory,
        final List<ShopItem> gameCatalog,
        final int gameLimit
    ) {
        return new EngineImpl(
            playerOps,
            gameStatus,
            gameBoard,
            gameMovementRules,
            gameRevealStrategy,
            gameStart,
            gameExit,
            gameShopFactory,
            gameCatalog,
            gameLimit
        );
    }

    private EngineImpl makeEngine() {
        return newEngine(
            this.player,
            this.status,
            this.board,
            this.rules,
            this.strategy,
            this.start,
            this.exit,
            this.shopFactory,
            this.globalCatalog,
            this.shopLimit
        );
    }

    @Test
    void contructorShouldThrowIfAnyDependencyNull() {
        final EngineImpl base = makeEngine();
        assertNotNull(base);
        assertThrows(IllegalArgumentException.class, 
            () -> new EngineImpl(
                null,
                this.status,
                this.board,
                this.rules,
                this.strategy,
                this.start,
                this.exit,
                this.shopFactory,
                this.globalCatalog,
                this.shopLimit
            )
        );
        assertThrows(IllegalArgumentException.class, 
            () -> new EngineImpl(
                this.player,
                null,
                this.board,
                this.rules,
                this.strategy,
                this.start,
                this.exit,
                this.shopFactory,
                this.globalCatalog,
                this.shopLimit
            )
        );
        assertThrows(IllegalArgumentException.class, 
            () -> new EngineImpl(
                player,
                this.status,
                null,
                this.rules,
                this.strategy,
                this.start,
                this.exit,
                this.shopFactory,
                this.globalCatalog,
                this.shopLimit
            )
        );
        assertThrows(IllegalArgumentException.class, 
            () -> new EngineImpl(
                this.player,
                this.status,
                this.board,
                null,
                this.strategy,
                this.start,
                this.exit,
                this.shopFactory,
                this.globalCatalog,
                this.shopLimit
            )
        );
        assertThrows(IllegalArgumentException.class, 
            () -> new EngineImpl(
                this.player,
                this.status,
                this.board,
                this.rules,
                null,
                this.start,
                this.exit,
                this.shopFactory,
                this.globalCatalog,
                this.shopLimit
            )
        );
        assertThrows(IllegalArgumentException.class, 
            () -> new EngineImpl(
                this.player,
                this.status,
                this.board,
                this.rules,
                this.strategy,
                null,
                this.exit,
                this.shopFactory,
                this.globalCatalog,
                this.shopLimit
            )
        );
        assertThrows(IllegalArgumentException.class, 
            () -> new EngineImpl(
                this.player,
                this.status,
                this.board,
                this.rules,
                this.strategy,
                this.start,
                null,
                this.shopFactory,
                this.globalCatalog,
                this.shopLimit
            )
        );
        assertThrows(IllegalArgumentException.class, 
            () -> new EngineImpl(
                this.player,
                this.status,
                this.board,
                this.rules,
                this.strategy,
                this.start,
                this.exit,
                null,
                this.globalCatalog,
                this.shopLimit
            )
        );
        assertThrows(IllegalArgumentException.class, 
            () -> new EngineImpl(
                this.player,
                this.status,
                this.board,
                this.rules,
                this.strategy,
                this.start,
                this.exit,
                this.shopFactory,
                null,
                this.shopLimit
            )
        );
    }

    @Test
    void constructorShouldThrowIfCatalogEmpty() {
        assertThrows(IllegalArgumentException.class, 
            () -> newEngine(
                this.player, 
                this.status, 
                this.board, 
                this.rules, 
                this.strategy, 
                this.start, 
                this.exit, 
                this.shopFactory, 
                List.of(), 
                this.shopLimit
            )
        );
    }

    @Test
    void constructorShouldThrowIfShopLimitNegative() {
        assertThrows(IllegalArgumentException.class, 
            () -> newEngine(
                this.player, 
                this.status, 
                this.board, 
                this.rules, 
                this.strategy, 
                this.start, 
                this.exit, 
                this.shopFactory, 
                this.globalCatalog,
                -1
            )
        );
    }

    @Test
    void revealShouldCallStrategyAndReturnApplied() {
        final EngineImpl engine = makeEngine();
        final Position p = new Position(0, 1);
        final ActionResult ar = engine.reveal(p);
        assertEquals(LevelState.PLAYING, ar.levelState());
        assertEquals(ActionEffect.APPLIED, ar.effect());
        assertEquals(1, this.strategy.calls);
        assertEquals(p, this.strategy.lastPos);
    }

    @Test
    void toggleFlagShouldFlipFlagAndReturnConsequential() {
        final EngineImpl engine = makeEngine();
        final Position p = new Position(0, 1);
        final ActionResult firstAR = engine.toggleFlag(p);
        assertEquals(ActionEffect.APPLIED, firstAR.effect());
        assertTrue(this.board.getCell(p).isFlagged());
        final ActionResult secondAR = engine.toggleFlag(p);
        assertEquals(ActionEffect.REMOVED, secondAR.effect());
        assertFalse(this.board.getCell(p).isFlagged());
    }

    @Test
    void moveSHouldReturnResultFromMoveServiceWhenNotApplied() {
        final EngineImpl engine = makeEngine();
        final ActionResult ar = engine.move(new Position(99, 99));
        assertEquals(ActionEffect.INVALID, ar.effect());
        assertEquals(LevelState.PLAYING, engine.status().levelState());
        assertEquals(GameMode.LEVEL, engine.status().gameMode());
    }

    @Test
    void moveShouldNotEnterShopIfExitNotReached() {
        final EngineImpl engine = makeEngine();
        this.rules.path = Optional.of(List.of(new Position(0, 1)));
        final ActionResult ar = engine.move(new Position(0, 1));
        assertEquals(ActionEffect.APPLIED, ar.effect());
        assertEquals(LevelState.PLAYING, engine.status().levelState());
        assertEquals(GameMode.LEVEL, engine.status().gameMode());
        assertTrue(engine.state().shop().isEmpty());
        assertEquals(new Position(0, 1), engine.player().position());
    }

    @Test
    void moveShouldEnterShopWhenExitReached() {
        final EngineImpl engine = makeEngine();
        this.rules.path = Optional.of(List.of(new Position(0, 1), new Position(0, 2)));
        final ActionResult ar = engine.move(new Position(0, 2));
        assertEquals(ActionEffect.APPLIED, ar.effect());
        assertEquals(LevelState.WON, engine.status().levelState());
        assertEquals(GameMode.SHOP, engine.status().gameMode());
        assertTrue(engine.state().shop().isPresent());
        final Shop shop = engine.state().shop().get();
        assertEquals(3, shop.items().size());
        assertEquals(this.shopLimit, shop.remainingPurchases());
    }

    @Test
    void buyShouldThrowIfTypeNull() {
        final EngineImpl engine = makeEngine();
        assertThrows(IllegalArgumentException.class, 
            () -> engine.buy(null)
        );
    }

    @Test
    void buyShouldThrowIfNotInShopMode() {
        final EngineImpl engine = makeEngine();
        assertThrows(IllegalStateException.class, 
            () -> engine.buy(StubItem.A)
        );
    }

    @Test
    void buyShouldUpdatePlayerAndShopWhenApplied() {
        final PlayerOperations richPlayer = new PlayerImpl(
            this.start, 
            3, 
            999, 
            new InventoryImpl()
        );
        final EngineImpl engine = new EngineImpl(
            richPlayer, 
            this.status, 
            this.board, 
            this.rules, 
            this.strategy, 
            this.start, 
            this.exit, 
            this.shopFactory, 
            this.globalCatalog, 
            this.shopLimit
        );
        this.rules.path = Optional.of(List.of(new Position(0, 1), new Position(0, 2)));
        engine.move(new Position(0, 2));
        assertEquals(GameMode.SHOP, engine.status().gameMode());
        assertTrue(engine.state().shop().isPresent());
        final var shopBefore = engine.state().shop().get();
        final ItemTypes toBuy = shopBefore.items().get(0).type();
        final int price = shopBefore.items().get(0).price();
        final int goldBefore = engine.player().goldCount();
        final int quantityBefore = engine.player().inventory().quantity(toBuy);
        final int remainingBefore = shopBefore.remainingPurchases();
        final ShopActionResult res = engine.buy(toBuy);
        assertEquals(ShopActionEffect.APPLIED, res.effect());
        assertEquals(goldBefore - price, engine.player().goldCount());
        assertEquals(quantityBefore + 1, engine.player().inventory().quantity(toBuy));
        assertTrue(engine.state().shop().isPresent());
        assertEquals(remainingBefore - 1, engine.state().shop().get().remainingPurchases());
    }

    @Test
    void leaveShopShouldThrowIfNoteInSHopMode() {
        final EngineImpl engine = makeEngine();
        assertThrows(IllegalStateException.class, engine::leaveShop);
    }

    @Test
    void leaveSHopShouldResetModeAndIncreaseLevelNumber() {
        final EngineImpl engine = makeEngine();
        this.rules.path = Optional.of(List.of(new Position(0, 1), new Position(0, 2)));
        engine.move(new Position(0, 2));
        final int prevLevel = engine.status().levelNumber();
        assertEquals(GameMode.SHOP, engine.status().gameMode());
        assertTrue(engine.state().shop().isPresent());
        engine.leaveShop();
        assertEquals(GameMode.LEVEL, engine.status().gameMode());
        assertEquals(LevelState.PLAYING, engine.status().levelState());
        assertEquals(prevLevel + 1, engine.status().levelNumber());
        assertTrue(engine.state().shop().isEmpty());
    }

    private enum StubItem implements ItemTypes {
        A, B, C, D;

        @Override
        public PlayerOperations applyEffect(final PlayerOperations stubPlayer) {
            if (stubPlayer == null) {
                throw new IllegalArgumentException("player");
            }
            return stubPlayer;
        }

        @Override
        public String shortString() {
            return name();
        }

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ROOT);
        }

        @Override
        public KindOfItem getItem() {
            throw new UnsupportedOperationException("Unimplemented method 'getItem'");
        }

    }

    private static final class TestRules implements MovementRules {

        private static final boolean CAN_ENTER = true;
        private Optional<List<Position>> path = Optional.empty();
        private final Set<Position> warnings = Set.of();

        @Override
        public Optional<List<Position>> pathCalculation(
            final Position from,
            final Position to,
            final Player player
        ) {
            return path;
        }

        @Override
        public boolean canEnter(final Position from, final Position to, final Player player) {
            return CAN_ENTER;
        }

        @Override
        public boolean mustStopOn(final Position p, final Player player) {
            return warnings.contains(p);
        }

        @Override
        public boolean isReachable(final Position from, final Position to, final Player player) {
            throw new UnsupportedOperationException("isReachable is not used");
        }

        @Override
        public Optional<Position> nextUnitaryStep(final Position from, final Position to, final Player player) {
            throw new UnsupportedOperationException("nextUnitaryStep is not used");
        }
    }

    private static final class TestStrategy implements RevealStrategy {

        private int calls;
        private Position lastPos;

        @Override
        public void reveal(final Board b, final Position p) {
            this.calls++;
            this.lastPos = p;
            b.getCell(p).reveal();
        }
    }

    private static final class TestCell implements Cell {

        private boolean flagged;
        private boolean revealed;

        private int adjacentTraps;
        private Optional<CellContent> content = Optional.empty();

        TestCell(final boolean flagged, final boolean revealed) {
            this.flagged = flagged;
            this.revealed = revealed;
            this.adjacentTraps = 0;
        }

        @Override
        public void reveal() {
            if (!this.flagged) {
                this.revealed = true;
            }
        }

        @Override
        public boolean isRevealed() {
            return this.revealed;
        }

        @Override
        public void toggleFlag() {
            if (!this.revealed) {
                this.flagged = !this.flagged;
            }
        }

        @Override
        public boolean isFlagged() {
            return this.flagged;
        }

        @Override
        public int getAdjacentTraps() {
            return this.adjacentTraps;
        }

        @Override
        public void setAdjacentTraps(final int n) {
            if (n < 0 || n > 8) {
                throw new IllegalArgumentException("adjacentTraps must be in [0,8]");
            }
            this.adjacentTraps = n;
        }

        @Override
        public boolean hasContent() {
            return this.content.isPresent();
        }

        @Override
        public Optional<CellContent> getContent() {
            return this.content;
        }

        @Override
        public void setContent(final CellContent content) {
            if (this.content.isPresent()) {
                throw new IllegalStateException("cell already has content");
            }
            this.content = Optional.ofNullable(content);
        }

        @Override
        public void removeContent() {
            this.content = Optional.empty();
        }
    }

    private static final class TestBoard implements Board {
        private final Set<Position> validPos;
        private final Map<Position, TestCell> cells;
        private final Map<Cell, Position> reverse;

        TestBoard(
            final Set<Position> validPos,
            final Map<Position, TestCell> cells
        ) {
            this.validPos = validPos;
            this.cells = new HashMap<>(cells);
            this.reverse = new HashMap<>();
            for (final var e : this.cells.entrySet()) {
                this.reverse.put(e.getValue(), e.getKey());
            }
        }

        @Override
        public boolean isPositionValid(final Position p) {
            return this.validPos.contains(p);
        }

        @Override
        public Cell getCell(final Position p) {
            return this.cells.get(p);
        }

        @Override
        public Position getCellPosition(final Cell cell) {
            return this.reverse.get(cell);
        }

        @Override
        public List<Cell> getBoardCells() {
            return this.validPos.stream()
                .sorted((a, b) -> {
                    final int cmpX = Integer.compare(a.x(), b.x());
                    return cmpX != 0 ? cmpX : Integer.compare(a.y(), b.y());
                })
                .map(this::getCell)
                .toList();
        }

        @Override
        public List<Cell> getAdjacentCells(final Position p) {
            throw new UnsupportedOperationException("getAdjacentCells is not used");
        }

        @Override
        public int getBoardSize() {
            throw new UnsupportedOperationException("getBoardSize is not used");
        }

        @Override
        public List<Cell> getRow(final int index) {
            throw new UnsupportedOperationException("getRow is not used");
        }

        @Override
        public List<Cell> getColumn(final int index) {
            throw new UnsupportedOperationException("getColumn is not used");
        }

        @Override
        public boolean isAdjacent(final Position p1, final Position p2) {
            throw new UnsupportedOperationException("isAdjacent is not used");
        }
    }
}

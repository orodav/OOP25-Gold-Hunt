package it.unibo.goldhunt.engine.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
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

public class EngineTest {

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

    private enum StubItem implements ItemTypes {
        A, B, C, D;

        @Override
        public PlayerOperations applyEffect(PlayerOperations player) {
            if (player == null) {
                throw new IllegalArgumentException("player");
            }
            return player;
        }

        @Override
        public String shortString() {
            return name();
        }

        @Override
        public String getName() {
            return name().toLowerCase();
        }

        @Override
        public KindOfItem getItem() {
            return getItem();
        }
    }

    private static final class TestRules implements MovementRules {
        Optional<List<Position>> path = Optional.empty();
        boolean canEnter = true;
        Set<Position> warnings = Set.of();

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
            return canEnter;
        }

        @Override
        public boolean mustStopOn(final Position p, final Player player) {
            return warnings.contains(p);
        }

        @Override
        public boolean isReachable(Position from, Position to, Player player) {
            throw new UnsupportedOperationException("not needed in engine tests");
        }

        @Override
        public Optional<Position> nextUnitaryStep(Position from, Position to, Player player) {
            throw new UnsupportedOperationException("not needed in engine tests");
        }
    }

    private static final class TestStrategy implements RevealStrategy {

        int calls;
        Position lastPos;

        @Override
        public void reveal(final Board b, final Position p) {
            this.calls++;
            this.lastPos = p;
        }
    }

    private static final class TestCell implements Cell {

        private boolean flagged;
        private boolean revealed;

        TestCell(final boolean flagged, final boolean revealed) {
            this.flagged = flagged;
            this.revealed = revealed;
        }

        @Override
        public void reveal() {
            if (!flagged) {
                revealed = true;
            }
        }

        @Override
        public boolean isRevealed() {
            return revealed;
        }

        @Override
        public void toggleFlag() {
            if (!revealed) {
                flagged = !flagged;
            }
        }

        @Override
        public boolean isFlagged() {
            return flagged;
        }

        @Override
        public int getAdjacentTraps() {
            throw new UnsupportedOperationException("not needed in engine tests");
        }

        @Override
        public void setAdjacentTraps(int n) {
        }

        @Override
        public boolean hasContent() {
            return false;
        }

        @Override
        public Optional<CellContent> getContent() {
            return Optional.empty();
        }

        @Override
        public void setContent(CellContent content) {
            throw new UnsupportedOperationException("not needed in engine tests");
        }

        @Override
        public void removeContent() {
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
            throw new UnsupportedOperationException("not needed in engine tests");
        }

        @Override
        public List<Cell> getAdjacentCells(Position p) {
            throw new UnsupportedOperationException("not needed in engine tests");
        }

        @Override
        public int getBoardSize() {
            throw new UnsupportedOperationException("not needed in engine tests");
        }

        @Override
        public List<Cell> getRow(int index) {
            throw new UnsupportedOperationException("not needed in engine tests");
        }

        @Override
        public List<Cell> getColumn(int index) {
            throw new UnsupportedOperationException("not needed in engine tests");
        }

        @Override
        public boolean isAdjacent(Position p1, Position p2) {
            throw new UnsupportedOperationException("not needed in engine tests");
        }
    }

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
            new ShopItem(StubItem.A, 3),
            new ShopItem(StubItem.B, 5),
            new ShopItem(StubItem.C, 7),
            new ShopItem(StubItem.D, 9)
        );
        this.shopFactory = (player, catalog, maxPurchases)
                -> new ShopImpl(player, catalog, maxPurchases);
    }

    private EngineImpl newEngine(
        final PlayerOperations player,
        final Status status,
        final Board board,
        final MovementRules movementRules,
        final RevealStrategy revealStrategy,
        final Position start,
        final Position exit,
        final ShopFactory shopFactory,
        final List<ShopItem> catalog,
        final int limit
    ) {
        return new EngineImpl(
            player,
            status,
            board,
            movementRules,
            revealStrategy,
            start,
            exit,
            shopFactory,
            catalog,
            limit
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
        engine.leaveShop();;
        assertEquals(GameMode.LEVEL, engine.status().gameMode());
        assertEquals(LevelState.PLAYING, engine.status().levelState());
        assertEquals(prevLevel + 1, engine.status().levelNumber());
        assertTrue(engine.state().shop().isEmpty());
    }
}

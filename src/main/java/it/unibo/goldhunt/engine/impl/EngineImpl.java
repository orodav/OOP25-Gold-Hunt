package it.unibo.goldhunt.engine.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.RevealStrategy;
import it.unibo.goldhunt.board.impl.ReadOnlyBoardAdapter;
import it.unibo.goldhunt.engine.api.ActionEffect;
import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.engine.api.ActionType;
import it.unibo.goldhunt.engine.api.EngineWithState;
import it.unibo.goldhunt.engine.api.GameMode;
import it.unibo.goldhunt.engine.api.EngineWithState.EngineWithShopActions;
import it.unibo.goldhunt.engine.api.GameState;
import it.unibo.goldhunt.engine.api.LevelState;
import it.unibo.goldhunt.engine.api.MovementRules;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.engine.api.StopReason;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.PlayerOperations;
import it.unibo.goldhunt.shop.api.Shop;
import it.unibo.goldhunt.shop.api.ShopActionEffect;
import it.unibo.goldhunt.shop.api.ShopActionResult;
import it.unibo.goldhunt.shop.api.ShopFactory;
import it.unibo.goldhunt.shop.api.ShopItem;

/**
 * Default engine implementation that coordinates movement, reveal actions,
 * and shop flow while exposing a read-only game state snapshot.
 * 
 * <p>
 * This class delegates movement and reveal logic to dedicated services
 * and handles transitions between {@link GameMode#LEVEL} and {@link GameMode#SHOP}.
 */
public class EngineImpl implements EngineWithShopActions {

    private static final int CATALOG_SIZE = 4;
    private PlayerOperations player;
    private Status status;
    private final Board board;
    private Optional<Shop> shop;
    private final Position start;
    private final Position exit;
    private final MoveService moveService;
    private final RevealService revealService;
    private final ShopFactory shopFactory;
    private final List<ShopItem> globalCatalog;
    private final int shopLimit;
    private final MovementRules rules;
    private final RevealStrategy revealStrategy;

    /**
     * Creates an engine with the provided dependencies and initial state.
     * 
     * @param player the initial player instance
     * @param status the initial status
     * @param board the game board
     * @param rules the movement rules strategy
     * @param revealStrategy the reveal strategy
     * @param start the starting position
     * @param exit the exit position
     * @param shopFactory the factory used to create shop sessions
     * @param globalCatalog the global catalog containing all shop items
     * @param shopLimit the maximum number of purchases allowed per shop session
     * @throws IllegalArgumentException if any dependency is {@code null}, 
     *                                  if {@code globalCatalog} is empty,
     *                                  or if {@code shopLimit} is negative
     */
    public EngineImpl(
        final PlayerOperations player,
        final Status status,
        final Board board,
        final MovementRules rules,
        final RevealStrategy revealStrategy,
        final Position start,
        final Position exit,
        final ShopFactory shopFactory,
        final List<ShopItem> globalCatalog,
        final int shopLimit
    ) {
        if (
            player == null || status == null || board == null || rules == null 
            || revealStrategy == null || start == null || exit == null
            || shopFactory == null || globalCatalog == null
        ) {
            throw new IllegalArgumentException("engine dependencies can't be null");
        }
        if (globalCatalog.isEmpty()) {
            throw new IllegalArgumentException("catalog can't be empty");
        }
        if (shopLimit < 0) {
            throw new IllegalArgumentException("shopLimit must be >= 0");
        }
        this.player = player;
        this.status = status;
        this.board = board;
        this.start = start;
        this.exit = exit;
        this.shop = Optional.empty();
        this.shopFactory = shopFactory;
        this.globalCatalog = List.copyOf(globalCatalog);
        this.shopLimit = shopLimit;
        this.rules = rules;
        this.revealStrategy = revealStrategy;
        this.moveService = new MoveService(
            board, 
            rules, 
            () -> this.player, 
            p -> { 
                this.player = p;
                return p;
            },
            () -> this.status
        );
        this.revealService = new RevealService(
            board, 
            revealStrategy, 
            () -> this.player, 
            p -> { 
                this.player = p;
                return p;
            },
            () -> this.status
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerOperations player() {
        return this.player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Status status() {
        return this.status;
    }

    /**
     * Returns the starting position of the current level.
     * 
     * @return the initial player position
     */
    Position start() {
        return this.start;
    }

    /**
     * Returns the exit position of the current level.
     * 
     * @return the level exit position
     */
    Position exit() {
        return this.exit;
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * This implementation additionally updates the {@link LevelState} to
     * {@link LevelState#LOSS} if the player runs out of lives
     * as a consequence of the reveal.
     */
    @Override
    public ActionResult reveal(final Position p) {
        final ActionResult res = this.revealService.reveal(p);
        if (res.effect() == ActionEffect.APPLIED) {
            this.updateLossIfNeeded();
            return new ActionResult(
                res.type(),
                res.reason(),
                this.status.levelState(),
                res.effect()
            );
        }
        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionResult toggleFlag(final Position p) {
       return this.revealService.toggleFlag(p);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * When the player reaches the exit, the engine transitions to
     * {@link GameMode#SHOP}, sets the level state to {@link LevelState#WON}
     * and initializes the shop session.
     */
    @Override
public ActionResult move(final Position newPos) {
    Objects.requireNonNull(newPos, "newPos can't be null");

    final Position from = this.player.position();
    final Optional<List<Position>> maybePath = this.rules.pathCalculation(from, newPos, this.player);

    if (maybePath.isEmpty()) {
        return new ActionResult(
            ActionType.MOVE,
            StopReason.NO_AVAILABLE_PATH,
            this.status.levelState(),
            ActionEffect.BLOCKED
        );
    }

    final List<Position> path = maybePath.get();
    final int startIdx = !path.isEmpty() && path.get(0).equals(from) ? 1 : 0;

    if (startIdx >= path.size()) {
        return new ActionResult(
            ActionType.MOVE,
            StopReason.ALREADY_THERE,
            this.status.levelState(),
            ActionEffect.BLOCKED
        );
    }

    ActionResult result = new ActionResult(
        ActionType.MOVE,
        StopReason.ALREADY_THERE,
        this.status.levelState(),
        ActionEffect.BLOCKED
    );

    for (int i = startIdx; i < path.size(); i++) {
        final Position step = path.get(i);

        result = this.moveService.move(step);
        if (result.effect() != ActionEffect.APPLIED) {
            return result;
        }
        this.revealService.reveal(step);

        this.updateLossIfNeeded();
        if (this.status.levelState() == LevelState.LOSS) {
            return new ActionResult(
                result.type(),
                result.reason(),
                this.status.levelState(),
                result.effect()
            );
        }
        if (this.rules.mustStopOn(step, this.player)) {
            return new ActionResult(
                result.type(),
                result.reason(),
                this.status.levelState(),
                result.effect()
            );
        }
        if (this.player.position().equals(this.exit)) {
            this.updateLossIfNeeded();
            this.status = this.status
                .withLevelState(LevelState.WON)
                .withGameMode(GameMode.SHOP);
            this.shop = Optional.of(
                this.shopFactory.create(
                    this.player,
                    this.shopItems(this.status.levelNumber()),
                    this.shopLimit
                )
            );
            return new ActionResult(
                result.type(),
                result.reason(),
                this.status.levelState(),
                result.effect()
            );
        }
    }
    if (!this.player.position().equals(this.exit)) {
        return result;
    }
    this.updateLossIfNeeded();
    this.status = this.status
        .withLevelState(LevelState.WON)
        .withGameMode(GameMode.SHOP);
    this.shop = Optional.of(
        this.shopFactory.create(
            this.player,
            this.shopItems(this.status.levelNumber()),
            this.shopLimit
        )
    );
    return new ActionResult(
        result.type(),
        result.reason(),
        this.status.levelState(),
        result.effect()
    );
}


    /**
     * {@inheritDoc}
     */
    @Override
    public GameState state() {
        return new GameStateImpl(
            new ReadOnlyBoardAdapter(this.board),
            this.player,
            this.status,
            this.shop
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopActionResult buy(final ItemTypes type) {
        if (type == null) {
            throw new IllegalArgumentException("type can't be null");
        }
        if (this.status.gameMode() != GameMode.SHOP || this.shop.isEmpty()) {
            throw new IllegalStateException("not in shop mode");
        }
        final Shop currentShop = this.shop.get();
        final ShopActionResult res = currentShop.buy(type);
        if (res.effect() == ShopActionEffect.APPLIED) {
            this.player = res.player();
            this.shop = Optional.of(res.shop());
            this.updateLossIfNeeded();
        }
        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leaveShop() {
        if (this.status.gameMode() != GameMode.SHOP) {
            throw new IllegalStateException("not in shop mode");
        }
        this.shop = Optional.empty();
        this.status = this.status
            .withGameMode(GameMode.LEVEL)
            .withLevelNumber(this.status.levelNumber() + 1)
            .withLevelState(LevelState.PLAYING);
        this.player = this.player.moveTo(this.start);
    }

    private List<ShopItem> shopItems(final int levelNumber) {
        final int size = this.globalCatalog.size();
        if (size != CATALOG_SIZE) {
            throw new IllegalStateException("globalCatalog must contain all items");
        }
        final int excluded = Math.floorMod(levelNumber - 1, size);
        return IntStream.range(0, size)
                .filter(i -> i != excluded)
                .mapToObj(this.globalCatalog::get)
                .toList();
    }

    private void updateLossIfNeeded() {
        if (this.status.levelState() == LevelState.PLAYING && this.player.livesCount() <= 0) {
            this.status = this.status.withLevelState(LevelState.LOSS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EngineWithState withPlayer(final PlayerOperations newPlayer) {
        Objects.requireNonNull(newPlayer, "player can't be null");
        final EngineImpl copy = new EngineImpl(
            player, 
            this.status, 
            this.board, 
            this.rules, 
            this.revealStrategy, 
            this.start, 
            this.exit, 
            this.shopFactory, 
            this.globalCatalog, 
            this.shopLimit
        );
        copy.shop = this.shop;
        return copy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EngineWithState withShop(final Optional<Shop> newShop) {
        Objects.requireNonNull(newShop, "shop can't be null");
        final EngineImpl copy = new EngineImpl(
            this.player, 
            this.status, 
            this.board, 
            this.rules, 
            this.revealStrategy, 
            this.start, 
            this.exit, 
            this.shopFactory, 
            this.globalCatalog, 
            this.shopLimit
        );
        copy.shop = newShop;
        return copy;
    }
}

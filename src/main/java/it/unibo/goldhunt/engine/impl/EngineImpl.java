package it.unibo.goldhunt.engine.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.RevealStrategy;
import it.unibo.goldhunt.engine.api.ActionEffect;
import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.engine.api.GameMode;
import it.unibo.goldhunt.engine.api.EngineWithState.EngineWithShopActions;
import it.unibo.goldhunt.engine.api.GameState;
import it.unibo.goldhunt.engine.api.LevelState;
import it.unibo.goldhunt.engine.api.MovementRules;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.Player;
import it.unibo.goldhunt.player.api.PlayerOperations;
import it.unibo.goldhunt.shop.api.Shop;
import it.unibo.goldhunt.shop.api.ShopActionEffect;
import it.unibo.goldhunt.shop.api.ShopActionResult;
import it.unibo.goldhunt.shop.api.ShopFactory;
import it.unibo.goldhunt.shop.api.ShopItem;

public class EngineImpl implements EngineWithShopActions {

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
    private static final int CATALOG_SIZE = 4;

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
            p -> { this.player = p; return p;},
            () -> this.status
        );
    }

    @Override
    public Player player() {
        return this.player;
    }

    @Override
    public Status status() {
        return this.status;
    }

    Position start() {
        return this.start;
    }

    Position exit() {
        return this.exit;
    }

    @Override
    public ActionResult reveal(Position p) {
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

    @Override
    public ActionResult toggleFlag(Position p) {
       return this.revealService.toggleFlag(p);
    }

    @Override
    public ActionResult move(final Position newPos) {
        final ActionResult result = this.moveService.move(newPos);
        if (result.effect() != ActionEffect.APPLIED) {
            return result;
        }
        this.updateLossIfNeeded();
        if (this.status.levelState() == LevelState.LOSS) {
            return new ActionResult(
                result.type(),
                result.reason(),
                this.status.levelState(),
                result.effect()
            );
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

    @Override
    public GameState state() {
        return new GameStateImpl(
            new ReadOnlyBoardAdapter(this.board),
            this.player,
            this.status,
            this.shop
        );
    }

    @Override
    public ShopActionResult buy(ItemTypes type) {
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
}


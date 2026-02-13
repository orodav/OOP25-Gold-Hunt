package it.unibo.goldhunt.view.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import it.unibo.goldhunt.board.api.ReadOnlyBoard;
import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.engine.api.LevelState;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.KindOfItem;
import it.unibo.goldhunt.player.api.PlayerOperations;
import it.unibo.goldhunt.root.GameSession;
import it.unibo.goldhunt.shop.api.Shop;
import it.unibo.goldhunt.shop.api.ShopActionEffect;
import it.unibo.goldhunt.shop.api.ShopActionResult;
import it.unibo.goldhunt.shop.api.ShopItem;
import it.unibo.goldhunt.view.api.ViewStateMapper;
import it.unibo.goldhunt.view.viewstate.CellViewState;
import it.unibo.goldhunt.view.viewstate.GameViewState;
import it.unibo.goldhunt.view.viewstate.HudViewState;
import it.unibo.goldhunt.view.viewstate.InventoryItemViewState;
import it.unibo.goldhunt.view.viewstate.InventoryViewState;
import it.unibo.goldhunt.view.viewstate.ScreenType;
import it.unibo.goldhunt.view.viewstate.ShopItemViewState;
import it.unibo.goldhunt.view.viewstate.ShopViewState;

/**
 * Default implementation of {@link ViewStateMapper}
 * 
 * <p>
 * This class translates the current {@link GameSession}
 * and action results into immutable {@link GameViewState}
 * instances for the UI layer.
 * This class only reads the session state and formats UI messages.
 */
public class ViewStateMapperImpl implements ViewStateMapper {

    private static final String STYLE_HIDDEN = "cell.hidden";
    private static final String STYLE_REVEALED = "cell.revealed";
    private static final String STYLE_FLAGGED = "cell.flagged";
    private static final String STYLE_PLAYER = "cell.player";

    /**
     * {@inheritDoc}
     */
    @Override
    public GameViewState fromSession(
        final GameSession session,
        final Optional<String> message,
        final ScreenType screen
    ) {
        Objects.requireNonNull(session, "session can't be null");
        Objects.requireNonNull(message, "message can't be null");
        Objects.requireNonNull(screen, "screen can't be null");
        final ReadOnlyBoard board = session.engine().state().board();
        final int size = board.boardSize();
        final PlayerOperations player = session.player();
        final Position playerPos = player.position();
        final Position exitPos = session.level().getExit();
        final LevelState levelState = session.status().levelState();
        final List<CellViewState> cells = buildCells(board, size, playerPos, exitPos);
        final HudViewState hud = new HudViewState(
            session.status().levelNumber(),
            player.livesCount(),
            player.goldCount()
        );
        final Optional<Shop> otherShop = session.shop();
        final Optional<ShopViewState> shopView = otherShop.map(
            s -> buildShopViewState(s, player)
        );
        final InventoryViewState inv = buildInventoryViewState(player, screen);
        return new GameViewState(
            size,
            cells,
            playerPos,
            hud,
            inv,
            shopView,
            message,
            screen,
            levelState
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<String> messageFromActionResult(final ActionResult result) {
        Objects.requireNonNull(result, "result can't be null");
        return switch (result.effect()) {
            case APPLIED -> Optional.empty();
            case REMOVED -> Optional.of("Successfully removed");
            case NONE -> Optional.of("Nothing to signal");
            case INVALID -> Optional.of("Invalid action");
            case BLOCKED -> Optional.of(
                    switch (result.reason()) {
                        case BLOCKED -> "Action blocked";
                        case ALREADY_THERE -> "You are already there";
                        case NO_AVAILABLE_PATH -> "No available path";
                        case ON_WARNING -> "Warning cell";
                        case REACHED_CELL -> "Destination cell reached";
                        case NONE -> "Action blocked";
                    }
                );
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<String> messageFromShopActionResult(final ShopActionResult result) {
        Objects.requireNonNull(result, "result can't be null");
        if (result.effect() == ShopActionEffect.APPLIED) {
            return Optional.of(
                "Purchase completed. Remaining purchases: "
                + result.remainingPurchases()
            );
        }
        return Optional.of(
            switch (result.reason()) {
                case NOT_ENOUGH_GOLD -> "Not enough gold";
                case LIMIT_REACHED -> "Purchase limit reached";
                case ALREADY_BOUGHT -> "Item already bought";
                case ITEM_NOT_SOLD -> "Item not sold in this shop";
                case NONE -> "Purchase not completed";
            }
        );
    }

    private List<CellViewState> buildCells(
        final ReadOnlyBoard board,
        final int size,
        final Position playerPos,
        final Position exitPos
    ) {
        return IntStream.range(0, size * size)
                .mapToObj(i -> {
                    final int x = i / size;
                    final int y = i % size;
                    final Position pos = new Position(x, y);
                    final var cell = board.cellAt(pos);
                    final boolean revealed = cell.isRevealed();
                    final boolean flagged = cell.isFlagged();
                    final int adjacent = cell.getAdjacentTraps();
                    final Optional<String> contentID = cell.contentID();
                    final String symbol = computeCellSymbol(
                        pos,
                        exitPos,
                        revealed,
                        flagged,
                        adjacent,
                        contentID
                    );
                    final String style = computeCellStyle(pos, revealed, flagged, playerPos);
                    return new CellViewState(
                        pos,
                        revealed,
                        flagged,
                        adjacent,
                        symbol,
                        style
                    );
                }).toList();
    }

    private String computeCellSymbol(
        final Position pos,
        final Position exitPos,
        final boolean revealed,
        final boolean flagged,
        final int adjacent,
        final Optional<String> contentID
    ) {
        if (pos.equals(exitPos)) {
            return "E";
        }
        if (!revealed) {
            return flagged ? "F" : "";
        }
        if (contentID.isPresent()) {
            final String id = contentID.get();
            return id.isEmpty() ? "" : id.substring(0, 1).toUpperCase(Locale.ROOT);
        }
        return adjacent > 0 ? Integer.toString(adjacent) : "";
    }

    private String computeCellStyle(
        final Position pos,
        final boolean revealed,
        final boolean flagged,
        final Position playerPos
    ) {
        if (pos.equals(playerPos)) {
            return STYLE_PLAYER;
        }
        if (!revealed) {
            return flagged ? STYLE_FLAGGED : STYLE_HIDDEN;
        }
        return STYLE_REVEALED;
    }

    private ShopViewState buildShopViewState(
        final Shop shop,
        final PlayerOperations player
    ) {
        final int remaining = shop.remainingPurchases();
        final List<ShopItemViewState> items = shop.items().stream()
                .map(st -> toShopItemViewState(st, player, remaining))
                .toList();
        return new ShopViewState(remaining, items);
    }

    private ShopItemViewState toShopItemViewState(
        final ShopItem item,
        final PlayerOperations player,
        final int remainingPurchases
    ) {
        final var type = item.type();
        final int price = item.price();
        final boolean affordable = player.goldCount() >= price;
        final boolean enabled = affordable && remainingPurchases > 0;
        return new ShopItemViewState(
            type,
            type.getName(),
            type.shortString(),
            price,
            affordable,
            enabled
        );
    }

    private InventoryViewState buildInventoryViewState(
    final PlayerOperations player,
    final ScreenType screen
    ) {
        Objects.requireNonNull(player, "player can't be null");
        Objects.requireNonNull(screen, "screen can't be null");
        final boolean allowUse = screen == ScreenType.PLAYING;
        final List<InventoryItemViewState> items =
            Arrays.stream(KindOfItem.values())
                .map(type -> {
                    final int quantity = player.inventory().quantity(type);
                    final boolean usable = allowUse && quantity > 0;
                    return new InventoryItemViewState(
                        type,
                        type.getName(),
                        type.shortString(),
                        quantity,
                        usable,
                        false
                    );
                })
                .toList();
        return new InventoryViewState(items);
    }
}

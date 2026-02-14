package it.unibo.goldhunt.engine.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.board.api.RevealStrategy;
import it.unibo.goldhunt.engine.api.ActionEffect;
import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.engine.api.LevelState;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.items.api.CellContent;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.items.api.KindOfItem;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Service responsible for handling reveal and flag actions.
 * 
 * <p>
 * This component validates action preconditions, delegates reveal logic
 * to the configured {@link RevealStrategy}, applies cell content effects
 * to the player, and produces {@link ActionResult} instances.
 */
public class RevealService {

    private static final Set<ItemTypes> PICKUP_ITEMS = Set.of(
        KindOfItem.PICKAXE,
        KindOfItem.DYNAMITE,
        KindOfItem.SHIELD,
        KindOfItem.CHART,
        KindOfItem.LUCKYCLOVER
    );
    private final Board board;
    private final RevealStrategy revealStrategy;
    private final Supplier<PlayerOperations> player;
    private final UnaryOperator<PlayerOperations> setPlayer;
    private final Supplier<Status> status;

    /**
     * Creates a reveal service with the required dependencies.
     * 
     * @param board the game board
     * @param revealStrategy the strategy used to reveal cells
     * @param player supplier for accessing the current player
     * @param setPlayer operator used to update the player state
     * @param status supplier for accessing the current status
     * @throws IllegalArgumentException if any dependency is {@code null}
     */
    RevealService(
        final Board board,
        final RevealStrategy revealStrategy,
        final Supplier<PlayerOperations> player,
        final UnaryOperator<PlayerOperations> setPlayer,
        final Supplier<Status> status
    ) {
        if (
            board == null || revealStrategy == null || player == null 
            || setPlayer == null || status == null
        ) {
            throw new IllegalArgumentException("dependencies can't be null");
        }
        this.board = board;
        this.revealStrategy = revealStrategy;
        this.player = player;
        this.setPlayer = setPlayer;
        this.status = status;
    }

    /**
     * Reveals the cell at the specified position.
     * 
     * @param p the position to reveal
     * @return an {@link ActionResult} describing the outcome
     * @throws IllegalArgumentException if {@code p} is {@code null}
     */
    ActionResult reveal(final Position p) {
        final Optional<ActionResult> preconditions = checkRevealPreconditions(p);
        if (preconditions.isPresent()) {
            return preconditions.get();
        }
        if (!this.board.isPositionValid(p)) {
            return ActionResultsFactory.reveal(this.status.get(), ActionEffect.INVALID);
        }
        final Set<Position> revealedBefore = snapshotRevealedCells();

        final Cell cell = this.board.getCell(p);
        if (cell.isFlagged() || cell.isRevealed()) {
            return ActionResultsFactory.reveal(this.status.get(), ActionEffect.BLOCKED);
        }

        this.revealStrategy.reveal(this.board, p);

        final Set<Position> revealedAfter = snapshotRevealedCells();
        revealedAfter.removeAll(revealedBefore);
        if (!revealedAfter.isEmpty()) {
            revealedAfter.forEach(this::applyContentIfAny);
            return ActionResultsFactory.reveal(this.status.get(), ActionEffect.APPLIED);
        }
        return ActionResultsFactory.reveal(this.status.get(), ActionEffect.NONE);
    }

    /**
     * Toggles the flag state of the specified cell.
     * 
     * @param p the position of the cell
     * @return an {@link ActionResult} describing the outcome
     * @throws IllegalArgumentException if {@code p} is {@code null}
     */
    ActionResult toggleFlag(final Position p) {
        final Optional<ActionResult> preconditions = checkFlagPreconditions(p);
        if (preconditions.isPresent()) {
            return preconditions.get();
        }
        final Cell cell = this.board.getCell(p);
        if (cell.isRevealed()) {
            return ActionResultsFactory.flag(this.status.get(), ActionEffect.BLOCKED);
        }
        final boolean wasFlagged = cell.isFlagged();
        cell.toggleFlag();
        return ActionResultsFactory.flag(
            this.status.get(),
            wasFlagged ? ActionEffect.REMOVED : ActionEffect.APPLIED
        );
    }

    /**
     * cellContent may be either an ItemTypes or a trap/other content.
     * 
     * @param p the position of application
     */
    private void applyContentIfAny(final Position p) {
        final Cell cell = this.board.getCell(p);
        final Optional<CellContent> optionalCell = cell.getContent();
        if (optionalCell.isEmpty()) {
            return;
        }
        final CellContent cellContent = optionalCell.get();
        final PlayerOperations currentPlayer = this.player.get();
        PlayerOperations updatedPlayer;
        try {
            final ItemTypes item = (ItemTypes) cellContent;
            if (PICKUP_ITEMS.contains(item.getItem())) {
                updatedPlayer = item.toInventory(currentPlayer);
            } else {
                updatedPlayer = item.applyEffect(currentPlayer);
            }
        } catch (final ClassCastException notAnIventoryItem) {
            updatedPlayer = cellContent.applyEffect(currentPlayer);
        } 
        if (updatedPlayer == null) {
            throw new IllegalStateException(
                "applyEffect returned null for " + cellContent.getClass().getSimpleName()
            );
        }
        this.setPlayer.apply(updatedPlayer);
        cell.removeContent();
    }

    private Optional<ActionResult> checkRevealPreconditions(final Position p) {
        if (p == null) {
            throw new IllegalArgumentException("position can't be null");
        }
        final Status currentStatus = this.status.get();
        if (!this.board.isPositionValid(p)) {
            return Optional.of(
                ActionResultsFactory.reveal(currentStatus, ActionEffect.INVALID)
            );
        }
        if (currentStatus.levelState() != LevelState.PLAYING) {
            return Optional.of(
                ActionResultsFactory.reveal(currentStatus, ActionEffect.BLOCKED)
            );
        }
        return Optional.empty(); 
    }

    private Optional<ActionResult> checkFlagPreconditions(final Position p) {
        if (p == null) {
            throw new IllegalArgumentException("position can't be null");
        }
        final Status currentStatus = this.status.get();
        if (!this.board.isPositionValid(p)) {
            return Optional.of(
                ActionResultsFactory.flag(currentStatus, ActionEffect.INVALID)
            );
        }
        if (currentStatus.levelState() != LevelState.PLAYING) {
            return Optional.of(
                ActionResultsFactory.flag(currentStatus, ActionEffect.BLOCKED)
            );
        }
        return Optional.empty();
    }

    private Set<Position> snapshotRevealedCells() {
        final Set<Position> revealed = new HashSet<>();
        for (final Cell c : this.board.getBoardCells()) {
            if (c.isRevealed()) {
                revealed.add(this.board.getCellPosition(c));
            }
        }
        return revealed;
    }
}

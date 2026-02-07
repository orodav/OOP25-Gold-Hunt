package it.unibo.goldhunt.engine.impl;

import java.util.Optional;
import java.util.function.Supplier;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.board.api.RevealStrategy;
import it.unibo.goldhunt.engine.api.ActionEffect;
import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.engine.api.LevelState;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.player.api.PlayerOperations;

public class RevealService {

    private final Board board;
    private final RevealStrategy revealStrategy;
    private final Supplier<Status> status;
    //private final Supplier<PlayerOperations> player;

    RevealService(
        final Board board,
        final RevealStrategy revealStrategy,
        final Supplier<Status> status,
        final Supplier<PlayerOperations> player
    ) {
        if (board == null || revealStrategy == null || status == null || player == null) {
            throw new IllegalArgumentException("dependencies can't be null");
        }
        this.board = board;
        this.revealStrategy = revealStrategy;
        this.status = status;
        //this.player = player;
    }

    ActionResult reveal(final Position p) {
        final Optional<ActionResult> preconditions = checkRevealPreconditions(p);
        if (preconditions.isPresent()) {
            return preconditions.get();
        }
        final Cell cell = this.board.getCell(p);
        if (cell.isFlagged() || cell.isRevealed()) {
            return ActionResultsFactory.reveal(this.status.get(), ActionEffect.BLOCKED);
        }
        this.revealStrategy.reveal(this.board, p);
        // read cell content and apply effects to player/status
        // reveal to lose ?
        return ActionResultsFactory.reveal(this.status.get(), ActionEffect.APPLIED);
    }

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
}

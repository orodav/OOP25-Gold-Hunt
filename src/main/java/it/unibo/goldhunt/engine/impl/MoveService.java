package it.unibo.goldhunt.engine.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.engine.api.ActionEffect;
import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.engine.api.LevelState;
import it.unibo.goldhunt.engine.api.MovementRules;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.engine.api.StopReason;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Service responsible for handling player movement logic.
 * 
 * <p>
 * This component validates movement preconditions, compute path using
 * {@link MovementRules}, and updates the player state step-by-step.
 * It produces {@link ActionResult} instances describing the outcome.
 */
public class MoveService {

    private final Board board;
    private final MovementRules rules;
    private final Supplier<PlayerOperations> player;
    private final UnaryOperator<PlayerOperations> setPlayer;
    private final Supplier<Status> status;

    /**
     * Creates a movement service with the required dependencies.
     * 
     * @param board the game board
     * @param rules the movement rules strategy
     * @param player supplier for accessing the current player
     * @param setPlayer operator used to update the player state
     * @param status supplier for accessing the current status
     * @throws IllegalArgumentException if any dependency is {@code null}
     */
    MoveService(
        final Board board,
        final MovementRules rules,
        final Supplier<PlayerOperations> player,
        final UnaryOperator<PlayerOperations> setPlayer,
        final Supplier<Status> status
    ) {
        if (board == null || rules == null || player == null 
            || setPlayer == null || status == null) {
                throw new IllegalArgumentException("dependencies can't be null");
        }
        this.board = board;
        this.rules = rules;
        this.player = player;
        this.setPlayer = setPlayer;
        this.status = status;
    }

    /**
     * Attempts to move the player to the specified position.
     * 
     * @param newPos the target position
     * @return an {@link ActionResult} describing the outcome
     * @throws IllegalArgumentException if {@code newPos} is {@code null}
     */
    ActionResult move(final Position newPos) {
        if (newPos == null) {
            throw new IllegalArgumentException("newPos can't be null");
        }
        final Optional<ActionResult> before = checkMovePreconditions(newPos);
        if (before.isPresent()) {
            return before.get();
        }
        final Position from = this.player.get().position();
        if (from.equals(newPos)) {
            return ActionResultsFactory.move(
                this.status.get(),
                StopReason.ALREADY_THERE,
                ActionEffect.NONE
            );
        }
        return moveByOne(newPos);
    }

    private Optional<ActionResult> checkMovePreconditions(final Position newPos) {
        final Status actualStatus = this.status.get();
        if (!this.board.isPositionValid(newPos)) {
            return Optional.of(
                ActionResultsFactory.move(
                    actualStatus, 
                    StopReason.NONE, 
                    ActionEffect.INVALID
                )
            );
        }
        if (actualStatus.levelState() != LevelState.PLAYING) {
            return Optional.of(
                ActionResultsFactory.move(
                    actualStatus, 
                    StopReason.NONE, 
                    ActionEffect.BLOCKED
                )
            );
        }
        return Optional.empty();
    }

    private ActionResult moveByOne(final Position newPos) {
        final PlayerOperations currentPlayer = this.player.get();
        final Optional<List<Position>> optionalPath = this.rules.pathCalculation(
            currentPlayer.position(), 
            newPos,
            currentPlayer
        );
        if (optionalPath.isEmpty()) {
            return noPath();
        }
        return followPath(optionalPath.get(), currentPlayer);
    }

    private ActionResult followPath(
        final List<Position> path,
        final PlayerOperations initialPlayer
    ) {
        PlayerOperations currentPlayer = initialPlayer;
        for (final Position nextPosition : path) {
            final Optional<ActionResult> stepResult = applyStep(currentPlayer, nextPosition);
            if (stepResult.isPresent()) {
                return stepResult.get();
            }
            currentPlayer = this.player.get();
        }
        return reached();
    }

    private Optional<ActionResult> applyStep(
        final PlayerOperations currentPlayer,
        final Position nextPosition
    ) {
        if (!this.rules.canEnter(currentPlayer.position(), nextPosition, currentPlayer)) {
            return Optional.of(blocked());
        }
        this.setPlayer.apply(currentPlayer.moveTo(nextPosition));

        final PlayerOperations updatedPlayer = this.player.get();
        if (this.rules.mustStopOn(nextPosition, updatedPlayer)) {
            return Optional.of(warningStop());
        }
        return Optional.empty();
    }

    private ActionResult noPath() {
        return ActionResultsFactory.move(
            this.status.get(),
            StopReason.NO_AVAILABLE_PATH,
            ActionEffect.BLOCKED
        );
    }

    private ActionResult blocked() {
        return ActionResultsFactory.move(
            this.status.get(),
            StopReason.BLOCKED,
            ActionEffect.BLOCKED
        );
    }

    private ActionResult warningStop() {
        return ActionResultsFactory.move(
            this.status.get(),
            StopReason.ON_WARNING,
            ActionEffect.APPLIED
        );
    }

    private ActionResult reached() {
        return ActionResultsFactory.move(
            this.status.get(),
            StopReason.REACHED_CELL,
            ActionEffect.APPLIED
        );
    }
}

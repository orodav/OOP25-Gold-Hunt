package it.unibo.goldhunt.engine.impl;

import it.unibo.goldhunt.engine.api.ActionEffect;
import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.engine.api.ActionType;
import it.unibo.goldhunt.engine.api.Engine;
import it.unibo.goldhunt.engine.api.LevelState;
import it.unibo.goldhunt.engine.api.MovementRules;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.engine.api.RevealResult;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.engine.api.StopReason;
import it.unibo.goldhunt.player.api.Player;
import it.unibo.goldhunt.player.api.PlayerOperations;

public class EngineImpl implements Engine{

    private PlayerOperations player;
    private Status status;
    private final BoardFittizia board;
    private final MovementRules rules;

    public EngineImpl(
        final PlayerOperations player,
        final Status status,
        final BoardFittizia board,
        final MovementRules rules
    ) {
        if (player == null || status == null || board == null || rules == null) {
            throw new IllegalArgumentException("engine dependencies can't be null");
        }
        this.player = player;
        this.status = status;
        this.board = board;
        this.rules = rules;
    }

    @Override
    public Player player() {
        return this.player;
    }

    @Override
    public Status status() {
        return this.status;
    }

    @Override
    public ActionResult reveal(Position p) {
        if (p == null) {
            throw new IllegalArgumentException("position can't be null");
        }
        if (!this.board.isPositionValid(p)) {
            return new ActionResult(
                ActionType.REVEAL,
                StopReason.NONE,
                this.status.levelState(),
                ActionEffect.INVALID);
        }
        if (this.status.levelState() != LevelState.PLAYING) {
            return new ActionResult(
                ActionType.REVEAL,
                StopReason.NONE,
                this.status.levelState(),
                ActionEffect.BLOCKED);
        }
        final RevealResult revealResult = this.board.reveal(p, this.player);    //reveal verrà da futuro import da Azzurra
        return new ActionResult(
                ActionType.REVEAL,
                StopReason.NONE,
                revealResult.newLevelState(),
                revealResult.effect());
    }

    @Override
    public ActionResult toggleFlag(Position p) {
        if (p == null) {
            throw new IllegalArgumentException("position can't be null");
        }
        if (!this.board.isPositionValid(p)) {
            return new ActionResult(ActionType.FLAG, StopReason.NONE, this.status.levelState(), ActionEffect.INVALID);
        }
        final boolean changed = this.board.toggleFlag(p);
        return new ActionResult(
            ActionType.FLAG,
            StopReason.NONE,
            this.status.levelState(),
            changed ? ActionEffect.APPLIED : ActionEffect.REMOVED
        );
    }

    @Override
    public ActionResult move(final Position target) {
        if (target == null) {
            throw new IllegalArgumentException("target can't be null");
        }
        if (!this.board.isPositionValid(target)) {
            return new ActionResult(
                ActionType.MOVE,
                StopReason.NONE,
                this.status.levelState(),
                ActionEffect.INVALID
            );
        }
        if (this.status.levelState() != LevelState.PLAYING) {
            return new ActionResult(
                ActionType.MOVE,
                StopReason.NONE,
                this.status.levelState(),
                ActionEffect.BLOCKED
            );
        }
        final Position from = this.player.position();
        if (!this.rules.isReachable(from, target, this.player)) {
            return new ActionResult(
                ActionType.MOVE,
                StopReason.NONE,
                this.status.levelState(),
                ActionEffect.INVALID
            );
        }
        if (!this.rules.canEnter(from, target, this.player)) {
            return new ActionResult(
                ActionType.MOVE,
                StopReason.NONE,
                this.status.levelState(),
                ActionEffect.BLOCKED
            );
        }
        if (this.board.isBlockedFor(target, this.player)) {
            return new ActionResult(
                ActionType.MOVE,
                StopReason.NONE,
                this.status.levelState(),
                ActionEffect.BLOCKED
            );
        }
        this.player = this.player.moveTo(target);
        final boolean mustStop = this.rules.mustStopOn(target, this.player()) ||
                this.board.isStopCellFor(target, this.player);
        return new ActionResult(
            ActionType.MOVE,
            mustStop ? StopReason.BLOCKED : StopReason.NONE,
            this.status.levelState(),
            ActionEffect.APPLIED
        );
    }
}

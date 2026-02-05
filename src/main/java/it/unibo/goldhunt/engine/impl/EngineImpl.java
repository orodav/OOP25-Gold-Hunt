package it.unibo.goldhunt.engine.impl;

import it.unibo.goldhunt.engine.api.ActionEffect;
import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.engine.api.ActionType;
import it.unibo.goldhunt.engine.api.Engine;
import it.unibo.goldhunt.engine.api.MovementRules;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.engine.api.StopReason;
import it.unibo.goldhunt.player.api.Player;

public class EngineImpl implements Engine{

    private final Player player;
    private final Status status;
    private final BoardFittizia board;
    private final MovementRules rules;

    public EngineImpl(Player player, Status status, BoardFittizia board, MovementRules rules) {
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
        /*if (p == null) {
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
                revealResult.effect()); */
        throw new IllegalArgumentException();   //inserita per compilazione funzionante, da rimuovere
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
    public ActionResult move(Position target) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'move'");
    }
    
}

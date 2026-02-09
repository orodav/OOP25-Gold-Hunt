package it.unibo.goldhunt.engine.impl;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.RevealStrategy;
import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.engine.api.Engine;
import it.unibo.goldhunt.engine.api.GameState;
import it.unibo.goldhunt.engine.api.MovementRules;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.player.api.Player;
import it.unibo.goldhunt.player.api.PlayerOperations;

public class EngineImpl implements Engine{

    private PlayerOperations player;
    private Status status;
    private final Board board;
    private final Position start;
    private final Position exit;
    private final MoveService moveService;
    private final RevealService revealService;

    public EngineImpl(
        final PlayerOperations player,
        final Status status,
        final Board board,
        final MovementRules rules,
        final RevealStrategy revealStrategy,
        final Position start,
        final Position exit
    ) {
        if (player == null || status == null || board == null || rules == null ||
            revealStrategy == null || start == null || exit == null
        ) {
            throw new IllegalArgumentException("engine dependencies can't be null");
        }
        this.player = player;
        this.status = status;
        this.board = board;
        this.start = start;
        this.exit = exit;
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
            () -> this.status, 
            () -> this.player
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

    @Override
    public ActionResult reveal(Position p) {
       return this.revealService.reveal(p);
    }

    @Override
    public ActionResult toggleFlag(Position p) {
       return this.revealService.toggleFlag(p);
    }

    @Override
    public ActionResult move(final Position newPos) {
       return this.moveService.move(newPos);
    }

    @Override
    public GameState state() {
        return new GameStateImpl(this.board, this.player, this.status);
    }
}


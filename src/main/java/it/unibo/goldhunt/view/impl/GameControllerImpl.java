package it.unibo.goldhunt.view.impl;

import java.util.Objects;
import java.util.Optional;

import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.engine.api.Direction;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.root.GameSession;
import it.unibo.goldhunt.view.api.GameController;
import it.unibo.goldhunt.view.api.GuiCommand;
import it.unibo.goldhunt.view.viewstate.GameViewState;

/**
 * Default UI-facing controller implementation.
 * It delegates game actions to {@link GameSession}
 * and exposes an immutable {@link GameViewState} snapshot for the View.
 */
public class GameControllerImpl implements GameController {

    private final GameSession session;
    private final ViewStateMapper mapper;
    private GameViewState state;

    public GameControllerImpl(
        final GameSession session,
        final ViewStateMapper mapper,
        final GameViewState state
    ) {
        this.session = Objects.requireNonNull(session, "session can't be null");
        this.mapper = Objects.requireNonNull(mapper, "mapper can't be null");
        this.state = this.mapper.fromSession(this.session, Optional.empty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameViewState state() {
        return this.state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameViewState handle(final GuiCommand command) {
        Objects.requireNonNull(command, "command can't be null");
        this.state = command.apply(this);
        return this.state;
    }

    @Override
    public GameViewState handleMoveTo(Position pos) {
        throw new UnsupportedOperationException("Unimplemented method 'handleMoveTo'");
    }

    @Override
    public GameViewState handleMove(Direction dir) {
        throw new UnsupportedOperationException("Unimplemented method 'handleMove'");
    }

    @Override
    public GameViewState handleReveal(Position pos) {
        throw new UnsupportedOperationException("Unimplemented method 'handleReveal'");
    }

    @Override
    public GameViewState handleToggleFlag(Position pos) {
        throw new UnsupportedOperationException("Unimplemented method 'handleToggleFlag'");
    }

    @Override
    public GameViewState handleBuy(ItemTypes type) {
        throw new UnsupportedOperationException("Unimplemented method 'handleBuy'");
    }

    @Override
    public GameViewState handleUseItem(ItemTypes type, Optional<Position> target) {
        throw new UnsupportedOperationException("Unimplemented method 'handleUseItem'");
    }

    @Override
    public GameViewState handleContinue() {
        throw new UnsupportedOperationException("Unimplemented method 'handleContinue'");
    }

    @Override
    public GameViewState handleLeaveToMenu() {
        throw new UnsupportedOperationException("Unimplemented method 'handleLeaveToMenu'");
    }

    @Override
    public GameViewState handleNewGame(Difficulty difficulty) {
        throw new UnsupportedOperationException("Unimplemented method 'handleNewGame'");
    }

}

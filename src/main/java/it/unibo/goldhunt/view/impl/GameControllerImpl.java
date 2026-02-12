package it.unibo.goldhunt.view.impl;

import java.util.Objects;
import java.util.Optional;

import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.engine.api.LevelState;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.root.GameSession;
import it.unibo.goldhunt.view.api.GameController;
import it.unibo.goldhunt.view.api.GuiCommand;
import it.unibo.goldhunt.view.viewstate.GameViewState;
import it.unibo.goldhunt.view.viewstate.ScreenType;

/**
 * Default UI-facing controller implementation.
 * It delegates game actions to {@link GameSession}
 * and exposes an immutable {@link GameViewState} snapshot for the View.
 */
public class GameControllerImpl implements GameController {

    private final GameSession session;
    private final ViewStateMapper mapper;
    private GameViewState state;
    private ScreenType screen;

    public GameControllerImpl(
        final GameSession session,
        final ViewStateMapper mapper
    ) {
        this.session = Objects.requireNonNull(session, "session can't be null");
        this.mapper = Objects.requireNonNull(mapper, "mapper can't be null");
        this.screen = ScreenType.MENU;
        this.state = this.mapper.fromSession(this.session, Optional.empty(), this.screen);

        
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

    /**
     * {@inheritDoc}
     */
    @Override
    public GameViewState handleMoveTo(Position pos) {
        Objects.requireNonNull(pos, "pos can't be null");
        final ActionResult res = this.session.move(pos);
        return refresh(this.mapper.messageFromActionResult(res));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameViewState handleReveal(Position pos) {
        Objects.requireNonNull(pos, "pos can't be null");
        final ActionResult res = this.session.reveal(pos);
        return refresh(this.mapper.messageFromActionResult(res));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameViewState handleToggleFlag(Position pos) {
        Objects.requireNonNull(pos, "pos can't be null");
        final ActionResult res = this.session.toggleFlag(pos);
        return refresh(this.mapper.messageFromActionResult(res));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameViewState handleBuy(ItemTypes type) {
        Objects.requireNonNull(type, "type can't be null");
        return refresh(this.mapper.handleBuy(this.session, type));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameViewState handleUseItem(ItemTypes type, Optional<Position> target) {
        Objects.requireNonNull(type, "type can't be null");
        Objects.requireNonNull(target, "target can't be null");
        return refresh(this.mapper.handleUseItem(this.session, type, target));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameViewState handleContinue() {
        if (this.screen == ScreenType.SHOP) {
            this.screen = ScreenType.DIFFICULTY;
        } else if (this.screen == ScreenType.END) {
            this.screen = ScreenType.SHOP;
        }
        return refresh(Optional.empty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameViewState handleLeaveToMenu() {
        this.screen = ScreenType.MENU;
        return refresh(Optional.empty());
    }

    private GameViewState refresh(final Optional<String> message) {
        LevelState levelState = session.status().levelState();
        if (levelState != LevelState.PLAYING) {
            this.screen = ScreenType.END;
        }
        this.state = this.mapper.fromSession(this.session, message, this.screen);
        return this.state;
    }
}

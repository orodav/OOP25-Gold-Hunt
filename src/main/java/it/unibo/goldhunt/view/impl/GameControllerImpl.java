package it.unibo.goldhunt.view.impl;

import java.util.Objects;
import java.util.Optional;

import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.engine.api.LevelState;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.root.GameFactory;
import it.unibo.goldhunt.root.GameSession;
import it.unibo.goldhunt.view.api.GameController;
import it.unibo.goldhunt.view.api.GuiCommand;
import it.unibo.goldhunt.view.api.ViewStateMapper;
import it.unibo.goldhunt.view.viewstate.GameViewState;
import it.unibo.goldhunt.view.viewstate.ScreenType;

/**
 * Default UI-facing controller implementation.
 * It delegates game actions to {@link GameSession}
 * and exposes an immutable {@link GameViewState} snapshot for the View.
 */
public class GameControllerImpl implements GameController {

    private GameSession session;
    private final ViewStateMapper mapper;
    private final GameFactory factory;
    private GameViewState state;
    private ScreenType screen;

    /**
     * Creates a new controller bound to the given game session and ViewStateMapper.
     * 
     * @param factory the factory used to create new game sessions
     * @param session the game session managed by this controller
     * @param mapper the mapper used to convert the session into view states
     * @throws NullPointerException if session or mapper is null
     */
    public GameControllerImpl(
        final GameFactory factory,
        final GameSession session,
        final ViewStateMapper mapper
    ) {
        this.session = Objects.requireNonNull(session, "session can't be null");
        this.mapper = Objects.requireNonNull(mapper, "mapper can't be null");
        this.factory = Objects.requireNonNull(factory, "factory can't be null");
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
    public GameViewState handleMoveTo(final Position pos) {
        Objects.requireNonNull(pos, "pos value can't be null");
        final ActionResult res = this.session.move(pos);
        return refresh(this.mapper.messageFromActionResult(res));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameViewState handleReveal(final Position pos) {
        Objects.requireNonNull(pos, "pos can't be null");
        final ActionResult res = this.session.reveal(pos);
        return refresh(this.mapper.messageFromActionResult(res));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameViewState handleToggleFlag(final Position pos) {
        Objects.requireNonNull(pos, "pos can't be null");
        final ActionResult res = this.session.toggleFlag(pos);
        return refresh(this.mapper.messageFromActionResult(res));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameViewState handleBuy(final ItemTypes type) {
        Objects.requireNonNull(type, "type can't be null");
        final var res = this.session.buy(type);
        return refresh(this.mapper.messageFromShopActionResult(res));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameViewState handleUseItem(final ItemTypes type, final Optional<Position> target) {
        Objects.requireNonNull(type, "type can't be null");
        Objects.requireNonNull(target, "target can't be null");
        this.session.useItem(type);
        return refresh(Optional.empty());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameViewState handleContinue() {
        if (this.screen == ScreenType.SHOP) {
            this.session.leaveShop();
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
        final LevelState levelState = this.session.status().levelState();
        if (levelState == LevelState.WON || levelState == LevelState.LOSS) {
            this.screen = ScreenType.END;
        }
        this.state = this.mapper.fromSession(this.session, message, this.screen);
        return this.state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameViewState handleSetDifficulty(final Difficulty difficulty) {
        Objects.requireNonNull(difficulty, "difficulty can't be null");
        this.session = this.factory.newGame(difficulty);
        this.screen = ScreenType.PLAYING;
        return refresh(Optional.of("Set " + difficulty + "level"));
    }
}

package it.unibo.goldhunt.root;

import java.util.Objects;
import java.util.Optional;

import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.configuration.api.Level;
import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.engine.api.EngineWithState;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.player.api.Player;
import it.unibo.goldhunt.shop.api.Shop;

public final class GameSession {

    private final Difficulty difficulty;
    private final Level level;
    private final EngineWithState engine;

    public GameSession(
        final Difficulty difficulty,
        final Level level,
        final EngineWithState engine
    ) {
        this.difficulty = Objects.requireNonNull(difficulty, "difficulty can't be null");
        this.level = Objects.requireNonNull(level, "level can't be null");
        this.engine = Objects.requireNonNull(engine, "engine can't be null");
    }

    public Difficulty difficulty() {
        return this.difficulty;
    }

    public Level level() {
        return this.level;
    }

    public EngineWithState engine() {
        return this.engine;
    }

    public Optional<Shop> shop() {
        return this.engine.state().shop();
    }

    public Player player() {
        return this.engine.state().player();
    }

    public Status status() {
        return this.engine.state().status();
    }

    public ActionResult move(final Position p) {
        return this.engine.move(p);
    }

    public ActionResult reveal(final Position p) {
        return this.engine.reveal(p);
    }

    public ActionResult toggleFlag(final Position p) {
        return this.engine.toggleFlag(p);
    }
}

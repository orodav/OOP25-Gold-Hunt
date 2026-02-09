package it.unibo.goldhunt.root;

import java.util.Objects;

import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.configuration.api.Level;
import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.engine.api.Engine;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.player.api.Player;
import it.unibo.goldhunt.shop.api.Shop;

public final class GameSession {

    private final Difficulty difficulty;
    private final Level level;
    private final Engine engine;
    private final Shop shop;

    public GameSession(
        final Difficulty difficulty,
        final Level level,
        final Engine engine,
        final Shop shop
    ) {
        this.difficulty = Objects.requireNonNull(difficulty, "difficulty can't be null");
        this.level = Objects.requireNonNull(level, "level can't be null");
        this.engine = Objects.requireNonNull(engine, "engine can't be null");
        this.shop = Objects.requireNonNull(shop, "shop can't be null");
    }

    public Difficulty difficulty() {
        return this.difficulty;
    }

    public Level level() {
        return this.level;
    }

    public Engine engine() {
        return this.engine;
    }

    public Shop shop() {
        return this.shop;
    }

    public Player player() {
        return this.engine.player();
    }

    public Status status() {
        return this.engine.status();
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

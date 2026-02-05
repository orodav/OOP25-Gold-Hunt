package it.unibo.goldhunt.configuration.api;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.engine.api.Position;

public interface BoardGenerator {
    Board generate (LevelConfig config, Position start, Position exit);
}

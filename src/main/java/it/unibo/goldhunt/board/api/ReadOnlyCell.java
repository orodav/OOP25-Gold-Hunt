// AZZU

package it.unibo.goldhunt.board.api;

import java.util.Optional;

public interface ReadOnlyCell {

    boolean isRevealed();

    boolean isFlagged();

    int adjacentTraps();

    Optional<String> contentID();
}

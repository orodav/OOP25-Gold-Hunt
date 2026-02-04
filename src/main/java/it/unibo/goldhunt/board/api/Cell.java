// AZZU

package it.unibo.goldhunt.board.api;

import java.util.Optional;


public interface Cell {

    void reveal();

    boolean isRevealed();
    
    void toggleFlag();
    
    boolean isFlagged();

    int getAdjacentTraps();

    void setAdjacentTraps();

    boolean hasContent();

    Optional<CellContent> getContent();

    void setContent(CellContent content);

    void removeContent();
    
}

// AZZU

package it.unibo.goldhunt.board.impl;

import java.util.Optional;

import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.board.api.CellFactory;
import it.unibo.goldhunt.items.api.CellContent;

public class BaseCellFactory implements CellFactory {

    public Cell createCell() {
        return new BaseCell();
    }

    public Cell createCell(final Optional<CellContent> content) {
        return new BaseCell(content);
    }   
    
}

package it.unibo.goldhunt.board.api;

import java.util.List;

import it.unibo.goldhunt.engine.api.Position;

/**
 * This interface models a Minesweeper-like game's board.
 */
public interface Board {

    /**
     * Returns a {@link List} containing the board's cells.
     * 
     * @return a {@link List} of {@link Cell} with all the board's cells
     */
    List<Cell> getBoardCells();

    /**
     * Returns the cell in the specified {@link Position}.
     * 
     * @param p the cell's position
     * @return the cell in position {@code p}
     * @throws NullPointerException if {@code p} is {@code null}
     * @throws IndexOutOfBoundsException if {@code p} is out of the board's bounds
     */
    Cell getCell(Position p);

    /**
     * Returns the specified cell's position.
     * 
     * @param cell the cell
     * @return the cell's position
     * @throws NullPointerException if {@code cell} is {@code null}
     * @throws IllegalArgumentException if the cell is not in the board
     */
    Position getCellPosition(Cell cell);

    /**
     * Returns a {@link List} containing the adjacent cells
     * of the cell in the specified position.
     * 
     * @param p the cell's position
     * @return a {@Link List} of {@link Cell} containing the cell's adjacent cells
     * @throws NullPointerException if {@code p} is {@code null}
     * @throws IndexOutOfBoundsException if {@code p} is out of the board's bounds
     */
    List<Cell> getAdjacentCells(Position p);

    /**
     * Returns the board's dimension.
     * 
     * @return the number of rows and cells (it is the same as the board is shaped like a square)
     */
    int getBoardSize();

    /**
     * Returns a {@link List} containing the specified row's cells.
     * 
     * @param index the row index (0-based) 
     * @return a {@link List} of {@link Cell} with all the row's cells
     * @throws IndexOutOfBoundsException if {@code index} is out of the board's bounds
     */
    List<Cell> getRow(int index);

    /**
     * Returns a {@link List} containing the specified column's cells.
     * 
     * @param index the column index (0-based)
     * @return a {@link List} of {@link Cell} with all the column's cells
     * @throws IndexOutOfBoundsException if {@code index} is out of the board's bounds
     */ 
    List<Cell> getColumn(int index);

    /**
     * Checks if the specified position is in the board.
     * 
     * @param p the position in the board
     * @return true if the position is in the board, false otherwise
     * @throws NullPointerException if {@code p} is {@code null}
     */
    boolean isPositionValid(Position p);

    /**
     * Checks if the specified positions in the cell are adjacent.
     * 
     * @param p1 the first position 
     * @param p2 the second position
     * @return true if the positions are adjacent, false otherwise
     * @throws NullPointerException if {@code p1} or {@code p2} are {@code null}
     * @throws IndexOutOfBoundsException if at least one of the positions
     *     is out of the board's bounds
     */
    boolean isAdjacent(Position p1, Position p2);

}

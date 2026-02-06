//SARA
package it.unibo.goldhunt.configuration.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.board.api.CellFactory;
import it.unibo.goldhunt.board.impl.BaseCell;
import it.unibo.goldhunt.board.impl.SquareBoard;
import it.unibo.goldhunt.configuration.api.BoardGenerator;
import it.unibo.goldhunt.configuration.api.LevelConfig;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemFactory;
import it.unibo.goldhunt.items.impl.Item;
import it.unibo.goldhunt.items.impl.Trap;

/**
 * This class is the implementation of BoardGenerator.
 */
public class BoardGeneratorImpl implements BoardGenerator {
    
    private final CellFactory cellFactory;
    private final ItemFactory itemFactory;
    
    /**
     * Creates a new {@code BoardGeneratorImpl}.
     * 
     * @param cellFactory the factory used to create board cells
     * @param itemFactory the factory used to create items placed on the board
     */
    public BoardGeneratorImpl(CellFactory cellFactory, ItemFactory itemFactory) {
        this.cellFactory = cellFactory;
        this.itemFactory = itemFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Board generate(LevelConfig config, Position start, Position exit) {

        final int size = config.getBoardSize();

        Board board = SquareBoard.create(size);

        initializeBoard(board);

        Set<Cell> safePath = computeSafePath(board, start, exit);

        placeTraps(board, safePath, config);
        placeItems(board, safePath, config);

        computeAdjacentTraps(board);

        return board;
    }
    
    /**
     * Initializes the board by filling all positions with empty cells.
     * 
     * @param board the board to initialize
     */
    private void initializeBoard(Board board) {
        
        final int size = board.getBoardSize();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board.setCell(cellFactory.createCell(), new Position(row, col));
            }
        }
    }

    /**
     * Computes a safe path by using a DFS algorithm and stores it 
     * in a {@link Set} of {@link Cell}.
     * 
     * @param board the board on which the safepath is computed
     * @param start the starting position of the algorithm
     * @param exit the ending position of the algorithm
     * @return a {@link Set} containing all cells belonging to the safe path
     */
    private Set<Cell> computeSafePath(Board board, Position start, Position exit) {
        
        Set<Cell> safePath = new HashSet<>();
        
        dfs(board, start, exit, safePath);
        
        return safePath;
    }

    /**
     * Recursive implementation of the DFS algorithm used to compute a safe path.
     * Starting from the current position, the method adds the corresponding cell to the safe path,
     * then explores adjacent cells in random order untile the exit position is reached.
     * If a dead end is encountered, backtracking is performed by removing the current cell
     * from the safe path.
     * 
     * @param board the board on which the search is performed
     * @param current the current position explored by the algorithm
     * @param exit the target exit position
     * @param safePath the {@link Set} collecting the cells of the safe path
     * @return true if a path to the exit has been found, false otherwise
     */
    private boolean dfs(Board board, Position current, Position exit, Set<Cell> safePath) {
        Cell currentCell = board.getCell(current);

        if (current.equals(exit)) {             
            safePath.add(currentCell);
            return true;
        }

        safePath.add(currentCell);

        List<Cell> neighbors = new ArrayList<>(board.getAdjacentCells(current));
        Collections.shuffle(neighbors);

        for (Cell neighbor : neighbors) {
            Position neighborPos = board.getCellPosition(neighbor);
            if (!safePath.contains(neighbor)) {
                if (dfs(board, neighborPos, exit, safePath)) {
                    return true;
                }
            }
        }

        safePath.remove(currentCell);           
        return false;    
    }

    /**
     * Places traps in a randomly on the board, avoiding the cells of the safe path.
     * 
     * @param board the board on which traps are placed
     * @param safePath the {@link Set} of {@link Cell} that must remain trap-free
     * @param config the level configuration providing the number of traps
     */
    private void placeTraps(Board board, Set<Cell> safePath, LevelConfig config) {
        
        int trapCount = config.getTrapCount();

        List<Cell> availableCells = new ArrayList<>();

        for (Cell cell : board.getBoardCells()) {
            if (!safePath.contains(cell)) {
                availableCells.add(cell);
            }
        }

        Collections.shuffle(availableCells);

        for (int i = 0; i < trapCount; i++) {
            if (i >= availableCells.size()) {
                throw new IllegalStateException("Not enough free cells for traps");
            }

            availableCells.get(i).setContent(new Trap());
        }
    }

    /**
     * Places items randomly on the board, avoiding the cells of the safe path.
     *  
     * @param board the board on which items are places
     * @param safePath the {@link Set} of {@link Cell} that must remain trap-free
     * @param config the level configuration providing the number of traps
     */
    private void placeItems(Board board, Set<Cell> safePath, LevelConfig config) {
        
        List<Cell> availableCells = new ArrayList<>();  // lista che conterrà tutte le caselle che non fanno parte del safepath

        for (Cell cell : board.getBoardCells()) {
            if (!safePath.contains(cell)) {
                availableCells.add(cell);
            }
        }

        Collections.shuffle(availableCells);

        Map<String, Integer> contents = config.getItemConfig();

        int index = 0;
        for (Map.Entry<String, Integer> entry : contents.entrySet()) {
            
            String symbol = entry.getKey();
            int quantity = entry.getValue();
            
            for (int i = 0; i < quantity; i++) {
                if (index >= availableCells.size()) {
                    throw new IllegalStateException("Not enough free cells for items");
                }

                Cell cell = availableCells.get(index);
                Item item = itemFactory.generateItem(symbol);
                cell.setContent(item);
                index++;
            }
        }
    }

    /**
     * Computes the number of adjacent traps for each cell on the board.
     * 
     * @param board the board on which adjacent traps are computed
     */
    private void computeAdjacentTraps(Board board) {
        
        for (Cell cell : board.getBoardCells()) {
            Position pos = board.getCellPosition(cell);
            List<Cell> neighbors = board.getAdjacentCells(pos);

            int trapCount = 0;
            for (Cell neighbor : neighbors) {
                if (neighbor.hasContent() && neighbor.getContent().get() instanceof Trap) {
                    trapCount++;
                }
            }

            if (cell instanceof BaseCell baseCell) {
                baseCell.setAdjacentTraps(trapCount);
            }
        }
    } 
}

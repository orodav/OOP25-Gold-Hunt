//SARA
package it.unibo.goldhunt.configuration.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.BoardFactory;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.configuration.api.BoardGenerator;
import it.unibo.goldhunt.configuration.api.LevelConfig;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemFactory;
import it.unibo.goldhunt.items.api.TrapFactory;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * This class is the implementation of BoardGenerator.
 */
public class BoardGeneratorImpl implements BoardGenerator {

    private static final int MAX_SAFE_PATH_ATTEMPTS = 100;
    
    private final BoardFactory boardFactory;
    private final TrapFactory trapFactory;
    private final ItemFactory itemFactory;
    private final PlayerOperations player;
    
    /**
     * Creates a new {@code BoardGeneratorImpl}.
     * 
     * @param boardFactory the factory used to create board cells
     * @param itemFactory the factory used to create items placed on the board
     */
    public BoardGeneratorImpl(final BoardFactory boardFactory, final TrapFactory trapFactory, final ItemFactory itemFactory, final PlayerOperations player) {
        this.boardFactory = Objects.requireNonNull(boardFactory);
        this.trapFactory = Objects.requireNonNull(trapFactory);
        this.itemFactory = Objects.requireNonNull(itemFactory);
        this.player = Objects.requireNonNull(player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Board generate(LevelConfig config, Position start, Position exit) {

        final Board board = boardFactory.createEmptyBoard(config.getBoardSize());

        final int requiredCells = computeRequiredCells(config);
        final int boardCells = config.getBoardSize() * config.getBoardSize();

        if (requiredCells >= boardCells - 2) {
            throw new IllegalStateException("Configuration requires too many occupied cells");
        }

        final int maxPathLength = boardCells - requiredCells;

        Set<Cell> safePath = Set.of();
        boolean pathFound = false;

        for (int attempt = 0; attempt < MAX_SAFE_PATH_ATTEMPTS && !pathFound; attempt++) {
            try {
                safePath = computeSafePath(board, start, exit, maxPathLength);
                pathFound = true;
            } catch (IllegalStateException e) {

            }
        }

        if (!pathFound) {
            throw new IllegalStateException("Unable to compute a valid safe path after retries");
        }

        final List<Cell> availableCells = new ArrayList<>();
        for (Cell cell : board.getBoardCells()) {
            if (!safePath.contains(cell)) {
                availableCells.add(cell);
            }
        }

        Collections.shuffle(availableCells);

        placeTraps(availableCells, config.getTrapCount());
        placeItems(availableCells, config.getItemConfig());
        
        computeAdjacentTraps(board);

        return board;
    }

    /**
     * 
     * @param config
     * @return
     */
    private int computeRequiredCells(final LevelConfig config) {
        int required = config.getTrapCount();
        for (Integer quantity : config.getItemConfig().values()) {
            required += quantity;
        }
        return required;
    }
    
    /**
     * Computes a safe path by using a BFS algorithm and stores it 
     * in a {@link Set} of {@link Cell}.
     * 
     * @param board the board on which the safepath is computed
     * @param start the starting position of the algorithm
     * @param exit the ending position of the algorithm
     * @return a {@link Set} containing all cells belonging to the safe path
     * @throws IllegalStateException if no safe path can be found
     */
    private Set<Cell> computeSafePath(final Board board, final Position start, final Position exit, int maxPathLength) {
                
        final List<Position> path = bfs(board, start, exit, maxPathLength);

        if (path.isEmpty()) {
            throw new IllegalStateException("No safe path could be computed");
        }

        final Set<Cell> safePath = new HashSet<>();
        for (Position p : path) {
            safePath.add(board.getCell(p));
        }

        return safePath;
    }

    /**
     * Recursive implementation of the DFS algorithm used to compute a safe path.
     * Starting from the current position, the method adds the corresponding cell to the safe path,
     * then explores adjacent cells in random order until the exit position is reached.
     * If a dead end is encountered, backtracking is performed by removing the current cell
     * from the safe path.
     * 
     * @param board the board on which the search is performed
     * @param current the current position explored by the algorithm
     * @param exit the target exit position
     * @param safePath the {@link Set} collecting the cells of the safe path
     * @return true if a path to the exit has been found, false otherwise
     */
    private List<Position> bfs(final Board board, final Position start, final Position exit, int maxPathLength) {
        
        final List<Position> queue = new ArrayList<>();
        final Map<Position, Position> parent = new HashMap<>();
        final Set<Position> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        int index = 0;
        while (index < queue.size()) {
            final Position current = queue.get(index);

            if (current.equals(exit)) {
                break;
            }

            final List<Cell> neighbors = new ArrayList<>(board.getAdjacentCells(current));
            Collections.shuffle(neighbors);

            for (Cell neighbor : neighbors) {
                final Position next = board.getCellPosition(neighbor);

                if (!visited.contains(next)) {
                    visited.add(next);
                    parent.put(next, current);
                    queue.add(next);
                }
            }
            index++;
        }

        if (!visited.contains(exit)) {
            return List.of();
        }

        final List<Position> path = new ArrayList<>();
        Position step = exit;

        while (step != null) {
            path.add(0, step);
            if (step.equals(start)) break;
            step = parent.get(step);
        }

        if (path.size() > maxPathLength) {
            return List.of();
        }

        return path;    
    }

    /**
     * Places traps randomly on the board, avoiding the cells of the safe path.
     * 
     * @param board the board on which traps are placed
     * @param safePath the {@link Set} of {@link Cell} that must remain trap-free
     * @param config the level configuration providing the number of traps
     */
    private void placeTraps(final List<Cell> availableCells, final int trapCount) {
    
        if (trapCount > availableCells.size()) {
            throw new IllegalStateException("Not enough space for traps");
        }

        for (int i = 0; i < trapCount; i++) {
            availableCells.remove(0).setContent(trapFactory.createTrap(player));
        }
    } 

    /**
     * Places items randomly on the board, avoiding the cells of the safe path.
     *  
     * @param board the board on which items are places
     * @param safePath the {@link Set} of {@link Cell} that must remain trap-free
     * @param config the level configuration providing the number of traps
     */
    private void placeItems(List<Cell> available, Map<String, Integer> itemsConfig) {
        
        for (Map.Entry<String, Integer> entry : itemsConfig.entrySet()) {
            final String symbol = entry.getKey();
            final int quantity = entry.getValue();

            for (int i = 0; i < quantity; i++) {
                if (available.isEmpty()) {
                    throw new IllegalStateException("Not enough space for item: " + symbol);
                }
                available.remove(0).setContent(itemFactory.generateItem(symbol));
            }
        }
    }

    /**
     * Computes the number of adjacent traps for each cell on the board.
     * 
     * @param board the board on which adjacent traps are computed
     */
    private void computeAdjacentTraps(final Board board) {
        
        for (Cell cell : board.getBoardCells()) {
            int count = 0;
        
            final Position pos = board.getCellPosition(cell);
            final List<Cell> neighbors = board.getAdjacentCells(pos);
            
            for (Cell neighbor : neighbors) {
                if (neighbor.hasContent() && neighbor.getContent().get().isTrap()) {
                    count++;
                }
            }

            cell.setAdjacentTraps(count);
        }
    } 
}


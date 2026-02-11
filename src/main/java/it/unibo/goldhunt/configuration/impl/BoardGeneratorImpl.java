
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
 * This class is the implementation of {@link BoardGenerator} 
 * that generates boards with a safe path using BFS, places traps and
 * items randomly in available cells and computes adjacent traps for each 
 * cell.
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
     * @param trapFactory the factory used to create traps
     * @param itemFactory the factory used to create items placed on the board
     * @param player the player operations instance used for trap creation
     * @throws NullPointerException if any parameter is null
     */
    public BoardGeneratorImpl(final BoardFactory boardFactory, final TrapFactory trapFactory, 
        final ItemFactory itemFactory, final PlayerOperations player) {
            this.boardFactory = Objects.requireNonNull(boardFactory);
            this.trapFactory = Objects.requireNonNull(trapFactory);
            this.itemFactory = Objects.requireNonNull(itemFactory);
            this.player = Objects.requireNonNull(player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Board generate(final LevelConfig config, final Position start, final Position exit) {

        final Board board = boardFactory.createEmptyBoard(config.getBoardSize());

        final int requiredCells = computeRequiredCells(config);
        final int boardCells = config.getBoardSize() * config.getBoardSize();

        if (requiredCells >= boardCells - 2) {
            throw new IllegalStateException("Configuration requires too many occupied cells");
        }

        final int maxPathLength = boardCells - requiredCells;

        Set<Cell> safePath = Set.of();

        for (int attempt = 0; attempt < MAX_SAFE_PATH_ATTEMPTS; attempt++) {
            safePath = computeSafePath(board, start, exit, maxPathLength);
            if (!safePath.isEmpty()) {
                break;
            }
        }

        if (safePath.isEmpty()) {
            throw new IllegalStateException("Unable to compute a valid safe path after retries");
        }

        final List<Cell> availableCells = new ArrayList<>();
        for (final Cell cell : board.getBoardCells()) {
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
     * Computes the total number of cells required for traps and items.
     * 
     * @param config the level configuration
     * @return the total count of required cells
     */
    private int computeRequiredCells(final LevelConfig config) {
        int required = config.getTrapCount();
        for (final Integer quantity : config.getItemConfig().values()) {
            required += quantity;
        }
        return required;
    }

    /**
     * Computes a safe path by using a BFS algorithm, 
     * ensuring it does not exceed max length. 
     * 
     * @param board the board on which the safepath is computed
     * @param start the starting position of the algorithm
     * @param exit the ending position of the algorithm
     * @param maxPathLength the maximum allowed path length
     * @return a {@link Set} containing all cells belonging to the safe path
     * @throws IllegalStateException if no safe path can be found withing start and exit
     */
    private Set<Cell> computeSafePath(final Board board, final Position start, final Position exit, final int maxPathLength) {

        final List<Position> path = bfs(board, start, exit, maxPathLength);

        if (path.isEmpty()) {
            return Set.of();
        }

        final Set<Cell> safePath = new HashSet<>();
        for (final Position p : path) {
            safePath.add(board.getCell(p));
        }

        return safePath;
    }

    /**
     * BFS implementation to find a randomized safe path from start to exit.
     * Shuffles neighbors for variability; rejects paths exceeding max length.
     * 
     * @param board the board 
     * @param start the starting position
     * @param exit the target exit position
     * @param maxPathLength maximum path length
     * @return the path as {@link List} of {@link Position} or empty if invalid
     */
    private List<Position> bfs(final Board board, final Position start, final Position exit, final int maxPathLength) {

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

            for (final Cell neighbor : neighbors) {
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
            if (step.equals(start)) {
                break;
            }
            step = parent.get(step);
        }

        if (path.size() > maxPathLength) {
            return List.of();
        }

        return path;
    }

    /**
     * Places the specified number of traps in the first available cells.
     * 
     * @param availableCells list of cells available for placement (shuffled)
     * @param trapCount number of traps to place.
     * @throws IllegalStateException if not enough available cells
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
     * Places items according to the configuration in available cells.
     * 
     * @param available list of available cells (modified by removal)
     * @param itemsConfig map of item symbols to quantities
     * @throws IllegalStateException if not enough space for an item type
     */
    private void placeItems(final List<Cell> available, final Map<String, Integer> itemsConfig) {

        for (final Map.Entry<String, Integer> entry : itemsConfig.entrySet()) {
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
     * Computes and sets the number of adjacent traps for every cell 
     * on the board.
     * 
     * @param board the board on which adjacent traps are computed
     */
    private void computeAdjacentTraps(final Board board) {

        for (final Cell cell : board.getBoardCells()) {
            int count = 0;

            final Position pos = board.getCellPosition(cell);
            final List<Cell> neighbors = board.getAdjacentCells(pos);

            for (final Cell neighbor : neighbors) {
                if (neighbor.hasContent() && neighbor.getContent().get().isTrap()) {
                    count++;
                }
            }

            cell.setAdjacentTraps(count);
        }
    } 
}

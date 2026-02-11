package it.unibo.goldhunt.engine.impl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiPredicate;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.engine.api.MovementRules;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.player.api.Player;

/**
 * Default implementation of {@link MovementRules} based on board adjacency.
 * 
 * <p>
 * This implementation validates the movement.
 * Path computation is performed via BFS to obtain the shortest valid path
 * when a path exists.
 */
public final class MovementRulesImpl implements MovementRules {

    private final Board board;
    private final BiPredicate<Position, Player> blocked;
    private final BiPredicate<Position, Player> stop;

    /**
     * Creates a rule set with no additional blocking or stop constraints.
     * 
     * @param board the board used to validate positions and adjacency
     * @throws IllegalArgumentException if {@code board} is {@code null}
     */
    public MovementRulesImpl(final Board board) {
        if (board == null) {
            throw new IllegalArgumentException("board cannot be null");
        }
        this.board = board;
        this.blocked = (pos, player) -> false;
        this.stop = (pos, player) -> false;
    }

    /**
     * Creates a rule set with custom predicates for blocked and stop cells.
     * 
     * @param board the board used to validate positions and adjacency
     * @param blocked predicate determining whether a position is blocked
     * @param stop predicate determining whether movement must stop on a position
     * @throws IllegalArgumentException if any argument is {@code null}
     */
    public MovementRulesImpl(
        final Board board,
        final BiPredicate<Position, Player> blocked,
        final BiPredicate<Position, Player> stop
    ) {
        if (board == null) {
            throw new IllegalArgumentException("board cannot be null");
        }
        if (blocked == null) {
            throw new IllegalArgumentException("blocked rule cannot be null");
        }
        if (stop == null) {
            throw new IllegalArgumentException("stop rule cannot be null");
        }
        this.board = board;
        this.blocked = blocked;
        this.stop = stop;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canEnter(final Position from, final Position to, final Player player) {
        if (from == null || to == null || player == null) {
            throw new IllegalArgumentException("parameters can't be null");
        }
        if (from.equals(to)) {
            return false;
        }
        if (!this.board.isPositionValid(to)) {
            return false;
        }
        if (!this.board.isAdjacent(from, to)) {
            return false;
        }
        return !this.blocked.test(to, player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean mustStopOn(final Position p, final Player player) {
        if (p == null || player == null) {
            throw new IllegalArgumentException("null arguments not allowed");
        }
        if (!this.board.isPositionValid(p)) {
            return false;
        }
        return this.stop.test(p, player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isReachable(final Position from, final Position to, final Player player) {
        if (from == null || to == null || player == null) {
            throw new IllegalArgumentException("null arguments are not allowed");
        }
        if (!this.board.isPositionValid(from) || !this.board.isPositionValid(to)) {
            return false;
        }
        if (from.equals(to)) {
            return true;
        }
        return this.pathCalculation(from, to, player).isPresent(); 
    }

    /** 
     * Performs a BFS from {@code from} to {@code to}.
     * It returns a predecessor map that can be used to reconstruct a valid path if the target is reached
     * 
     * @param from the starting position
     * @param to the target position
     * @param player the player
     * @return an {@code Optional} valid path
     */
    private Optional<Map<Position, Position>> bfsFindPathTree(
        final Position from,
        final Position to,
        final Player player
    ) {
        final Set<Position> visited = new HashSet<>();
        final Queue<Position> queue = new ArrayDeque<>();
        final Map<Position, Position> predecessor = new HashMap<>();
        visited.add(from);
        queue.add(from);
        while (!queue.isEmpty()) {
            final Position currentPosition = queue.remove();
            for (final Cell adjacentCell : this.board.getAdjacentCells(currentPosition)) {
                final Position nextPosition = this.board.getCellPosition(adjacentCell);
                if (!visited.contains(nextPosition) 
                    && this.canEnter(currentPosition, nextPosition, player)
                ) {
                    visited.add(nextPosition);
                    predecessor.put(nextPosition, currentPosition);
                    if (nextPosition.equals(to)) {
                        return Optional.of(predecessor);
                    }
                    queue.add(nextPosition);
                } 
            }
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Position> nextUnitaryStep(
        final Position from,
        final Position to,
        final Player player
    ) {
        final Optional<List<Position>> optionalPath = this.pathCalculation(
            from, 
            to, 
            player
        );
        if (optionalPath.isEmpty()) {
            return Optional.empty();
        }
        final List<Position> path = optionalPath.get();
        return path.isEmpty() ? Optional.empty() : Optional.of(path.get(0));
    }

    private Optional<List<Position>> pathFromPred(
        final Position from,
        final Position to,
        final Map<Position, Position> pred
    ) {
        final List<Position> path = new ArrayList<>();
        Position current = to;
        while (!current.equals(from)) {
            path.add(0, current);
            current = pred.get(current);
            if (current == null) {
                return Optional.empty();
            }
        }
        return Optional.of(path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<Position>> pathCalculation(final Position from, final Position to, final Player player) {
        if (from == null || to == null || player == null) {
            throw new IllegalArgumentException("null arguments are not allowed");
        }
        if (!this.board.isPositionValid(from) || !this.board.isPositionValid(to)) {
            return Optional.empty();
        }
        if (from.equals(to)) {
            return Optional.of(List.of());
        }
        final Optional<Map<Position, Position>> optionalPredecessor = this.bfsFindPathTree(
            from,
            to, 
            player
        );
        if (optionalPredecessor.isEmpty()) {
            return Optional.empty();
        }
        final Optional<List<Position>> optionalPath = this.pathFromPred(
            from,
            to,
            optionalPredecessor.get()
        );
        return optionalPath;
    }
}

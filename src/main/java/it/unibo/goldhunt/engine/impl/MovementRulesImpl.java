package main.java.it.unibo.goldhunt.engine.impl;
//davv
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import it.unibo.goldhunt.engine.api.MovementRules;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.player.api.Player;

public class MovementRulesImpl implements MovementRules {

    private final BoardView board;

    public MovementRulesImpl(final BoardView board) {
        if(board == null) {
            throw new IllegalArgumentException("board can't be null");
        }
        this.board = board;
    }

    @Override
    public boolean canEnter(final Position from, final Position to, final Player player) {
        if (from == null || to == null) {
            throw new IllegalArgumentException();
        }
        if (player == null) {
            throw new IllegalArgumentException();
        }
        if (from.equals(to)) {
            return false;
        }
        /** condizioni legate a board:
         *  nomi fittizi per ora
         * 
        if (!this.board.isInside(to)) {
            return false;
        }
        if (!this.board.areAdjacent(from, to)) {
            return false;
        }
        return !this.board.isBlockedFor(to, player); */
        
        return false;   //inserito per compilazione, da rimuovere
    }

    @Override
    public boolean mustStopOn(final Position p, final Player player) {
        if (p == null || player == null) {
            throw new IllegalArgumentException("null arguments are not allowed");
        }
        return this.board.isStopCellFor(p, player);
    }

    @Override
    public boolean isReachable(final Position from, final Position to, final Player player) {
        if (from == null || to == null || player == null) {
            throw new IllegalArgumentException("null arguments are not allowed");
        }
        if (from.equals(to)) {
            return true;
        }    
        return this.bfsReachable(from, to, player); 
    }

    /** BFS reachability check.
     * 
     * Dependencies: 
     * - BoardView.neighborsOf() - hypothetical name
     * - canEnter() - local rule
     */
    private boolean bfsReachable(final Position from, final Position to, final Player player) {
        final Set<Position> visited = new HashSet<>();
        final Queue<Position> queue = new ArrayDeque<>();
        visited.add(from);
        queue.add(from);
        while (!queue.isEmpty()) {
            final Position current = queue.remove();
            for (final Position next : this.board.neighborsOf(current)) {                   //dependency
                 if (!visited.contains(next) && this.canEnter(current, next, player)) {
                    if (next.equals(to)) {
                        return true;
                    }
                    visited.add(next);
                    queue.add(next);
                }
            }
        }
        return false;
    }
}
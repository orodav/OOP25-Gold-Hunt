package it.unibo.goldhunt.engine.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.engine.api.ActionEffect;
import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.engine.api.GameMode;
import it.unibo.goldhunt.engine.api.LevelState;
import it.unibo.goldhunt.engine.api.MovementRules;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.engine.api.StopReason;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.Player;
import it.unibo.goldhunt.player.api.PlayerOperations;
import it.unibo.goldhunt.player.impl.InventoryImpl;
import it.unibo.goldhunt.player.impl.PlayerImpl;

public class MoveServiceTest {

    private Status status;
    private TestPlayer testPlayer;
    private TestBoard board;
    private TestMovRules rules;

    // helpers

    private static PlayerOperations makePlayer(final Position p) {
        final Inventory inventory = new InventoryImpl();
        return new PlayerImpl(p, 3, 0, inventory);
    }

    private static final class TestPlayer {

        private PlayerOperations player;

        TestPlayer(final PlayerOperations player) {
            this.player = player;
        }

        PlayerOperations get() {
            return this.player;
        }

        PlayerOperations set(final PlayerOperations pl) {
            this.player = pl;
            return pl;
        }
    }

    private static final class TestBoard implements Board {

        private final Set<Position> validPos;

        TestBoard(final Set<Position> validPos) {
            this.validPos = validPos;
        }

        @Override
        public boolean isPositionValid(final Position p) {
            return this.validPos.contains(p);
        }

        @Override
        public List<Cell> getBoardCells() {
            throw new UnsupportedOperationException("Unimplemented method 'getBoardCells'");
        }

        @Override
        public Cell getCell(Position p) {
            throw new UnsupportedOperationException("Unimplemented method 'getCell'");
        }

        @Override
        public Position getCellPosition(Cell cell) {
            throw new UnsupportedOperationException("Unimplemented method 'getCellPosition'");
        }

        @Override
        public List<Cell> getAdjacentCells(Position p) {
            throw new UnsupportedOperationException("Unimplemented method 'getAdjacentCells'");
        }

        @Override
        public int getBoardSize() {
            throw new UnsupportedOperationException("Unimplemented method 'getBoardSize'");
        }

        @Override
        public List<Cell> getRow(int index) {
            throw new UnsupportedOperationException("Unimplemented method 'getRow'");
        }

        @Override
        public List<Cell> getColumn(int index) {
            throw new UnsupportedOperationException("Unimplemented method 'getColumn'");
        }

        @Override
        public boolean isAdjacent(Position p1, Position p2) {
            throw new UnsupportedOperationException("Unimplemented method 'isAdjacent'");
        }
        
    }

    private static final class TestMovRules implements MovementRules {
        
        Optional<List<Position>> path = Optional.empty();
        boolean canEnter = true;
        Set<Position> warnings = Set.of();

        @Override
        public Optional<List<Position>> pathCalculation(
                    final Position from,
                    final Position to,
                    final Player player
        ) {
            return path;
        }

        @Override
        public boolean canEnter(final Position from, final Position to, final Player player) {
            return canEnter;
        }

        @Override
        public boolean mustStopOn(final Position p, final Player player) {
            return warnings.contains(p);
        }

        @Override
        public boolean isReachable(Position from, Position to, Player player) {
            throw new UnsupportedOperationException("Unimplemented method 'isReachable'");
        }

        @Override
        public Optional<Position> nextUnitaryStep(Position from, Position to, Player player) {
            throw new UnsupportedOperationException("Unimplemented method 'nextUnitaryStep'");
        }
    }

    @BeforeEach
    void init() {
        this.status = StatusImpl.createStartingState();
        this.testPlayer = new TestPlayer(makePlayer(new Position(0, 0)));
        this.board = new TestBoard(Set.of(
            new Position(0, 0),
            new Position(0, 1),
            new Position(0, 2)
        ));
        this.rules = new TestMovRules();
    }

    private MoveService makeService(
        final Board board,
        final MovementRules rules,
        final TestPlayer testPlayer,
        final Status status
    ) {
        return new MoveService(
            this.board,
            this.rules,
            this.testPlayer::get,
            this.testPlayer::set,
            () -> this.status);
    }
    
    @Test
    void moveShouldThrowIfNull() {
        assertThrows(IllegalArgumentException.class, 
            () -> makeService(
                this.board, this.rules, this.testPlayer, this.status
            ).move(null)
        );
    }

    @Test
    void shouldReturnBlockedWhenNotPlaying() {
        this.status = new StatusImpl(LevelState.WON, GameMode.LEVEL, 1);
        final ActionResult ar = makeService(
            this.board, this.rules, this.testPlayer, this.status
        )
        .move(new Position(0, 1));
        assertEquals(StopReason.NONE, ar.reason());
        assertEquals(LevelState.WON, ar.levelState());
        assertEquals(ActionEffect.BLOCKED, ar.effect());
        assertEquals(new Position(0, 0), this.testPlayer.get().position());
    }

    @Test
    void shouldReturnInvalidWhenTargetOutOfBounds() {
        final MoveService service = makeService(this.board, this.rules, this.testPlayer, this.status);
        final ActionResult ar = service.move(new Position(154, 132));
        assertEquals(StopReason.NONE, ar.reason());
        assertEquals(LevelState.PLAYING, ar.levelState());
        assertEquals(ActionEffect.INVALID, ar.effect());
        assertEquals(new Position(0, 0), this.testPlayer.get().position());
    }

    @Test
    void shouldReturnAlreadyThereWhenSamePosition() {
        final MoveService service = makeService(this.board, this.rules, this.testPlayer, this.status);
        final ActionResult ar = service.move(new Position(0, 0));
        assertEquals(StopReason.ALREADY_THERE, ar.reason());
        assertEquals(LevelState.PLAYING, ar.levelState());
        assertEquals(ActionEffect.NONE, ar.effect());
        assertEquals(new Position(0, 0), this.testPlayer.get().position());
    }

    @Test
    void shouldReturnNoAvailablePathWhenEmptyPath() {
        final MoveService service = makeService(this.board, this.rules, this.testPlayer, this.status);
        this.rules.path = Optional.empty();
        final ActionResult ar = service.move(new Position(0, 2));
        assertEquals(StopReason.NO_AVAILABLE_PATH, ar.reason());
        assertEquals(LevelState.PLAYING, ar.levelState());
        assertEquals(ActionEffect.BLOCKED, ar.effect());
        assertEquals(new Position(0, 0), this.testPlayer.get().position());
    }

    void shouldReturnBlockedWhenCannotEnterStep() {
        final MoveService service = makeService(this.board, this.rules, this.testPlayer, this.status);
        this.rules.path = Optional.of(List.of(new Position(0, 1)));
        this.rules.canEnter = false;
        final ActionResult ar = service.move(new Position(0, 1));
        assertEquals(StopReason.BLOCKED, ar.reason());
        assertEquals(LevelState.PLAYING, ar.levelState());
        assertEquals(ActionEffect.BLOCKED, ar.effect());
        assertEquals(new Position(0, 0), this.testPlayer.get().position());
    }

    @Test
    void shouldUpdatePlayerWhenStepsOk() {
        final MoveService service = makeService(this.board, this.rules, this.testPlayer, this.status);
        this.rules.path = Optional.of(List.of(new Position(0, 1), new Position(0, 2)));
        this.rules.canEnter = true;
        final ActionResult ar = service.move(new Position(0, 2));
        assertEquals(StopReason.REACHED_CELL, ar.reason());
        assertEquals(LevelState.PLAYING, ar.levelState());
        assertEquals(ActionEffect.APPLIED, ar.effect());
        assertEquals(new Position(0, 2), this.testPlayer.get().position());        
    }

    @Test
    void shouldStopOnWarningWhenMustStopOn() {
        final MoveService service = makeService(this.board, this.rules, this.testPlayer, this.status);
        this.rules.path = Optional.of(List.of(new Position(0, 1), new Position(0, 2)));
        this.rules.canEnter = true;
        this.rules.warnings = Set.of(new Position(0, 1));
        final ActionResult ar = service.move(new Position(0, 2));
        assertEquals(StopReason.ON_WARNING, ar.reason());
        assertEquals(LevelState.PLAYING, ar.levelState());
        assertEquals(ActionEffect.APPLIED, ar.effect());
        assertEquals(new Position(0, 1), this.testPlayer.get().position());
    }
}

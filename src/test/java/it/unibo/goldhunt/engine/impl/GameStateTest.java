package it.unibo.goldhunt.engine.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.ReadOnlyBoard;
import it.unibo.goldhunt.board.api.ReadOnlyCell;
import it.unibo.goldhunt.engine.api.GameMode;
import it.unibo.goldhunt.engine.api.LevelState;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.player.api.Player;
import it.unibo.goldhunt.player.impl.InventoryImpl;
import it.unibo.goldhunt.player.impl.PlayerImpl;
import it.unibo.goldhunt.shop.api.Shop;

public class GameStateTest {

    private static ReadOnlyBoard testBoard() {
        return new ReadOnlyBoard() {

            @Override
            public int boardSize() {
                throw new UnsupportedOperationException("not needed in test");
            }

            @Override
            public ReadOnlyCell cellAt(Position p) {
                throw new UnsupportedOperationException("not needed in test");
            } 
        };
    }

    private static Player testPlayer() {
        return new PlayerImpl(
            new Position(0, 0),
            3,
            0,
            new InventoryImpl()
        );
    }

    private static Status testStatus() {
        return new StatusImpl(LevelState.PLAYING, GameMode.LEVEL, 1);
    }


    @Test
    void constructorShouldThrowIfBoardNull() {
        assertThrows(NullPointerException.class, 
            () -> new GameStateImpl(
                null, 
                testPlayer(), 
                testStatus(), 
                Optional.empty()
            )
        );
    }

    @Test
    void counstructorShouldThrowIfPlayerNull() {
        assertThrows(NullPointerException.class, 
            () -> new GameStateImpl(
                testBoard(), 
                null, 
                testStatus(), 
                Optional.empty()
            )
        );
    }

    @Test
    void constructorShouldThrowIfStatusNull() {
        assertThrows(NullPointerException.class, 
            () -> new GameStateImpl(
                testBoard(), 
                testPlayer(), 
                null, 
                Optional.empty()
            )
        );
    }

    @Test
    void constructorShouldThrowIfShopNull() {
        assertThrows(NullPointerException.class, 
            () -> new GameStateImpl(
                testBoard(), 
                testPlayer(), 
                testStatus(), 
                null
            )
        );
    }

    @Test
    void shouldExposeAllFieldsCorrectly() {
        final ReadOnlyBoard board = testBoard();
        final Player player = testPlayer();
        final Status status = testStatus();
        final Optional<Shop> shop = Optional.empty();
        final GameStateImpl state = new GameStateImpl(
            board, 
            player, 
            status, 
            shop
        );
        assertSame(board, state.board());
        assertSame(player, state.player());
        assertSame(status, state.status());
        assertSame(shop, state.shop());
    }
}

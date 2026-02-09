package it.unibo.goldhunt.items.api;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;

public record ItemContext(Board board, PlayerOperations playerop, Inventory inventory) {

}

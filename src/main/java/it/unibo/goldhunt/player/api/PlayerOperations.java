package it.unibo.goldhunt.player.api;

import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;

public interface PlayerOperations extends Player{

    PlayerOperations moveTo(Position p);

    PlayerOperations addGold(int num);

    PlayerOperations addLives(int num);

    PlayerOperations addItem(ItemTypes item, int quantity);

    PlayerOperations useItem(ItemTypes item, int quantity);
}

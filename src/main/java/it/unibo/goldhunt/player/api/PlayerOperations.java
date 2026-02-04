package main.java.it.unibo.goldhunt.player.api;

import main.java.it.unibo.goldhunt.engine.api.Position;
import main.java.it.unibo.goldhunt.items.Item;

public interface PlayerOperations extends Player{

    PlayerOperations moveTo(Position p);

    PlayerOperations addGold(int num);

    PlayerOperations addLives(int num);

    PlayerOperations addItem(Item item, int quantity);

    PlayerOperations useItem(Item item, int quantity);
}

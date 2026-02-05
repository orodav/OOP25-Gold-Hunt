package it.unibo.goldhunt.items.api;

//luca

import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.player.api.Player;
import it.unibo.goldhunt.player.impl.InventoryImpl;

public abstract class Item implements ItemTypes{

    public int quantity;
    public static final int MAX_QUANTITY_CLOVER = 1;
    public static final int MAX_QUANTITY_SHIELD = 1;
    public static final int MAX_QUANTITY_ITEMS = 3;
    public static final int MAX_QUANTITY_LIVES = 3;

    public InventoryImpl inventory = new InventoryImpl();
    public Position position;
    public Player player;

    public void cannotUse(){
        System.out.println("invalid target");
    }
    
    public abstract boolean canUse();

}

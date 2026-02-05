package it.unibo.goldhunt.items.impl;
//luca
import it.unibo.goldhunt.player.impl.InventoryImpl;

public class GoldX3 extends Gold{
    
    InventoryImpl inventory = new InventoryImpl();

    @Override
    public void applyEffect(){

        inventory.add(gold, 3);
    }

}

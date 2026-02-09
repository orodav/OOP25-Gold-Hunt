
package it.unibo.goldhunt.items.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.items.api.ItemContext;
import it.unibo.goldhunt.items.api.ItemFactory;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;


public class ItemFactoryImpl implements ItemFactory{

    private final static Map<String, Supplier<Item>> ITEMS = new HashMap<>();


    static{
        ITEMS.put("M", Chart::new);
        ITEMS.put("D", Dynamite::new);
        ITEMS.put("G", Gold::new);
        ITEMS.put("L", Lifes::new);
        ITEMS.put("C", LuckyClover::new);
        ITEMS.put("P", Pickaxe::new);
        ITEMS.put("S", Shield::new);
        ITEMS.put("X", GoldX3::new);
    }
    @Override
    public Item generateItem(String item, Board board, PlayerOperations playerop, Inventory inventory){
        Supplier<Item> constructor = ITEMS.get(item);

        if(constructor == null){
            throw new IllegalArgumentException();
        }
        Item obj = constructor.get();
        obj.bind(new ItemContext(board, playerop, inventory));
        return obj;
    }
    @Override
    public Item generateItem(String item) {
        Supplier<Item> constructor = ITEMS.get(item);
            if (constructor == null) {
            throw new IllegalArgumentException("Unknown item symbol");
            }
        return constructor.get();
    }


}


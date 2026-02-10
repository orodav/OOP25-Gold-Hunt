
package it.unibo.goldhunt.items.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.items.api.ItemContext;
import it.unibo.goldhunt.items.api.ItemFactory;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Implementation of {@link ItemFactory}.
 * <p>
 * Creates game items based on a symbol and binds them to the context if provided.
 */
public class ItemFactoryImpl implements ItemFactory{

    private static final Map<String, Supplier<Item>> ITEMS = new HashMap<>();


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

    /**
     * Generates an item and binds it to the given context.
     *
     * @param item the symbol representing the item
     * @param board the game board
     * @param playerop the player operations
     * @param inventory the player inventory
     * @return the created {@link Item}
     * @throws IllegalArgumentException if the symbol is unknown
     */
    @Override
    public Item generateItem(final String item, final Board board, final PlayerOperations playerop, final Inventory inventory){
        final Supplier<Item> constructor = ITEMS.get(item);

        if(constructor == null){
            throw new IllegalArgumentException();
        }
        final Item obj = constructor.get();
        obj.bind(new ItemContext(board, playerop, inventory));
        return obj;
    }
    
    /**
     * Generates an item without binding a context.
     * 
     * @param item the symbol representing the item
     * @return the created {@link Item}
     * @throws IllegalArgumentException if the symbol is unknown
     */
    @Override
    public Item generateItem(final String item) {
        final Supplier<Item> constructor = ITEMS.get(item);
            if (constructor == null) {
            throw new IllegalArgumentException("Unknown item symbol");
            }
        return constructor.get();
    }


}


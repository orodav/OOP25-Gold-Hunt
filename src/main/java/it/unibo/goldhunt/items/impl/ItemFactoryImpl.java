
package it.unibo.goldhunt.items.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import it.unibo.goldhunt.items.api.ItemFactory;


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
    public Item generateItem(final String item){
        Supplier<Item> constructor = ITEMS.get(item);

        if(constructor == null){
            throw new IllegalArgumentException();
        }
        return constructor.get();
    }


}


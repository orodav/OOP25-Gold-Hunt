package it.unibo.goldhunt.items.impl;
//luca

import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemContext;
import it.unibo.goldhunt.items.api.ItemTypes;
public abstract class Item implements ItemTypes{

    protected ItemContext context;

    public static final int MAX_QUANTITY_CLOVER = 1;
    public static final int MAX_QUANTITY_SHIELD = 1;
    public static final int MAX_QUANTITY_ITEMS = 3;
    public static final int MAX_QUANTITY_LIVES = 3;
    public static final int ADDED_GOLD = 1;
    public static final int ADDED_GOLDX3 = 3;
    public static final int PLUS_LIFE = 1;
    public static final int RADIUS = 2;
    public static final int LUCKY_CLOVER_MULTIPLIER = 2;

    public Position position;

    public void bind(ItemContext context){
        this.context = context;
    }

}

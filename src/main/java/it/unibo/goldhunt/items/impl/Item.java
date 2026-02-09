package it.unibo.goldhunt.items.impl;
//luca

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.Player;
import it.unibo.goldhunt.player.api.PlayerOperations;

public abstract class Item implements ItemTypes{

    public static final int MAX_QUANTITY_CLOVER = 1;
    public static final int MAX_QUANTITY_SHIELD = 1;
    public static final int MAX_QUANTITY_ITEMS = 3;
    public static final int MAX_QUANTITY_LIVES = 3;
    public static final int ADDED_GOLD = 1;
    public static final int ADDED_GOLDX3 = 3;
    public static final int PLUS_LIFE = 1;
    public static final int RADIUS = 2;
    public static final int LUCKY_CLOVER_MULTIPLIER = 1;

    public Inventory inventory;
    public Position position;
    public Player player;
    public PlayerOperations playerop;
    public Trap trap;
    public Gold gold;
    public GoldX3 goldX3;
    public Board board;
    public LuckyClover luckyclover;


    public void cannotUse(){
        System.out.println("invalid target");
    }
    

}

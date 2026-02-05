package it.unibo.goldhunt.items.impl;
import it.unibo.goldhunt.items.api.Item;
import it.unibo.goldhunt.player.impl.PlayerImpl;
import it.unibo.goldhunt.engine.api.Position;
//luca galassi

public class Lifes extends Item{

    public static final String ITEM_NAME = "life";
    private boolean consumable = false;

    @Override
    public boolean isConsumable() {
        return consumable;
    }

    @Override
    public String getName() {
        return ITEM_NAME;
    }


    @Override
    public void applyEffect() {
        player.setLives(player.livesCount()+1);
    }

    @Override
    public boolean canUse() {
        return false;
    }

    @Override
    public String shortString() {
        return "L";
    }
}

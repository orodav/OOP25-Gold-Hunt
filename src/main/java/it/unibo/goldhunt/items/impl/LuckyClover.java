package it.unibo.goldhunt.items.impl;

import java.util.List;

public class LuckyClover extends Item{

    private final static String ITEM_NAME = "Lucky clover";

    @Override
    public String getName() {
        return ITEM_NAME;
    }

    @Override
    public boolean applyEffect() {
        List<Item> collected = List.of(gold, goldX3).stream()
        .filter(Item::applyEffect)
        .toList();

        collected.forEach(this::applyBonus);

        return !collected.isEmpty();
        }

    
    

    private void applyBonus(Item goldtype){
        if(inventory.hasAtLeast(luckyclover, MAX_QUANTITY_CLOVER)){
            inventory.add(goldtype, LUCKY_CLOVER_MULTIPLIER);
        }
    }

    @Override
    public String shortString() {
        return "C";
    }

}
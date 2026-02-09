package it.unibo.goldhunt.items.impl;

import it.unibo.goldhunt.items.api.Revealable;
import it.unibo.goldhunt.player.api.PlayerOperations;

//luca
public class Trap implements Revealable{
    
    public static final int DAMAGE = -1;

    PlayerOperations playerop;

    Trap(){

    }
    public void bind(PlayerOperations playerop){
        this.playerop = playerop;
    }
    


    @Override
    public boolean applyEffect() {
        if(playerop == null){
            throw new IllegalStateException("cannot bind items");
        }
        
        playerop.addLives(DAMAGE);
        return true;
    }

    @Override
    public String shortString() {
        return "T";
    }
}

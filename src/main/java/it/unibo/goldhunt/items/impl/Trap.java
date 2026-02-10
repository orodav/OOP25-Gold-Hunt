package it.unibo.goldhunt.items.impl;

import it.unibo.goldhunt.items.api.Revealable;
import it.unibo.goldhunt.player.api.PlayerOperations;

//luca
public class Trap implements Revealable{
    
    public static final int DAMAGE = -1;

    PlayerOperations playerop;

    /**
     * Private constructor
     */
    Trap(){

    }

    /**
     * Binds a player to this trap.
     * @param playerop the player to bind.
     */
    public void bind(PlayerOperations playerop){
        this.playerop = playerop;
    }
    
    /**
     * Reduces the player's health by the damage amount.
     * 
     * @param playerop the player who triggered the trap.
     * @return the updated player state.
     * @throws IllegalStateException if playerop is null.
     */
    @Override
    public PlayerOperations applyEffect(final PlayerOperations playerop) {
        if(playerop == null){
            throw new IllegalStateException("cannot bind items");
        }
        return playerop.addLives(DAMAGE);
    }

    /**
     * Returns a short string representing the item.
     * 
     * @return "T"
     */
    @Override
    public String shortString() {
        return "T";
    }

    /**
     * @return always true since this is a trap.
     */
    @Override
    public boolean isTrap(){
        return true;
    }
}

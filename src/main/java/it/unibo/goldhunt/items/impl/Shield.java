package it.unibo.goldhunt.items.impl;

import it.unibo.goldhunt.items.api.KindOfItem;
import it.unibo.goldhunt.items.api.Revealable;
import it.unibo.goldhunt.player.api.PlayerOperations;

//luca

/**
 * Implementation of the Shield item.
 * 
 * <p>
 * The shield protects the player by nullifying damage taken from traps.
 */
public class Shield extends AbstractItem {

    private static final String ITEM_NAME = "Shield";

    private Revealable trap;

    /**
     * Returns the name of the item.
     * 
     * @return "Shield"
     */
    @Override
    public String getName() {
        return ITEM_NAME;
    }

    /**
     * Applies the effect of a trap but restores any life lost, effectively nullifying the damage.
     * 
     * @param playerop the player to protect.
     * @return the player with restored health if a trap was triggered.
     * @throws IllegalStateException if the context is missing
     */
    @Override
    public PlayerOperations applyEffect(final PlayerOperations playerop) {
        if (getContext() == null) {
            throw new IllegalStateException("cannot bound item");
        }

        if (trap == null) {
            return null;
        }

        final int before = playerop.livesCount();
        final PlayerOperations afterTrap = trap.applyEffect(playerop);
        final int damageTaken = before - afterTrap.livesCount();

        if (damageTaken > 0) {
            return afterTrap.addLives(damageTaken);
        }
        return afterTrap;
    }

    /**
     * Returns a short string representing the item.
     * 
     * @return "S"
     */
    @Override
    public String shortString() {
        return "S";
    }

    /**
     * Connects a trap to this shield.
     * 
     * @param trapToBind the trap to bind.
     */
    public void bindTrap(final Revealable trapToBind) {
        this.trap = trapToBind;
    }

    /**
     * Returns the type of this item.
     * 
     * @return {@link KindOfItem#SHIELD}
     */
    @Override
    public KindOfItem getItem() {
        return KindOfItem.SHIELD;
    }

    @Override
    public PlayerOperations toInventory(final PlayerOperations playerop){
        return playerop.addItem(this.getItem(), 1);
    }
}

package it.unibo.goldhunt.player.impl;
//davv
import java.util.HashMap;
import java.util.Map;

import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.Inventory;

/**
 * Default immutable implementation of {@link Inventory}.
 * 
 * This implementation stores item quantities in an immutable internal map.
 */
public class InventoryImpl implements Inventory {

    private final Map<ItemTypes, Integer> items;

    /**
     * Creates an empty inventory.
     */
    public InventoryImpl() {
        this.items = Map.of();  //empty inventory - immutable 
    }

    private InventoryImpl(final Map<ItemTypes, Integer> items) {
        this.items = Map.copyOf(items);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Inventory add(final ItemTypes item, final int quantity) {
        if (item == null) {
            throw new IllegalArgumentException("item");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("quantity must be >= 0");
        }
        final Map<ItemTypes, Integer> newItems = new HashMap<>(this.items);
        newItems.put(item, this.quantity(item) + quantity);
        return new InventoryImpl(newItems);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Inventory remove(final ItemTypes item, final int quantity) {
        if (item == null) {
            throw new IllegalArgumentException("item");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be > 0");
        }
        final int current = this.quantity(item);
        if (current < quantity) {
            throw new IllegalArgumentException("not enough quantity in inventory");
        }
        final Map<ItemTypes, Integer> newItems = new HashMap<>(this.items);
        if (current == quantity) {
            newItems.remove(item);
        } else {
            newItems.put(item, current - quantity);
        }
        return new InventoryImpl(newItems);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int quantity(final ItemTypes item) {
        if (item == null) {
            throw new IllegalArgumentException("item");
        }
        final Integer key = this.items.get(item);
        return key == null ? 0 : key;
    }

}

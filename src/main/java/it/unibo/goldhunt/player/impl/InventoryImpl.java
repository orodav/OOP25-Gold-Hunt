package main.java.it.unibo.goldhunt.player.impl;
//davv
import java.util.HashMap;
import java.util.Map;

import it.unibo.goldhunt.player.api.Inventory;
//import it.unibo.goldhunt.player.api.Item;

public class InventoryImpl implements Inventory {

    private final Map<Item, Integer> items = new HashMap<>();

    @Override
    public void add(final Item item, final int quantity) {
        if (item == null) {
            throw new IllegalArgumentException("item");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be > 0");
        }
        final int addedQuantity = this.quantity(item);
        this.items.put(item, addedQuantity + quantity);
    }

    @Override
    public void remove(final Item item, final int quantity) {
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
        final int difference = current - quantity;
        if (difference == 0) {
            this.items.remove(item);
        } else {
            this.items.put(item, difference);
        }
    }

    @Override
    public int quantity(final Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item");
        }
        final Integer key = this.items.get(item);
        return key == null ? 0 : key;
    }

}

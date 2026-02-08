package it.unibo.goldhunt.items.api;

import it.unibo.goldhunt.items.impl.Item;

public interface ItemFactory {

    Item generateItem(final String item);
}

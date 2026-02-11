package it.unibo.goldhunt.view.viewmodel;

/**
 * Immutable snapshot describing how a single shop item should be 
 * displayed in the UI.
 */
public record ShopItemViewState(
    String name,
    String shortString,
    int price,
    boolean affordable,
    boolean enabled
) { }

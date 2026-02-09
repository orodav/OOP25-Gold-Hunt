package it.unibo.goldhunt.items.api;

public enum BaseItemType implements ItemTypes {

    SHIELD("Shield"),
    PICKAXE("Pickaxe"),
    DYNAMITE("Dynamite"),
    CHART("Chart");

    private final String name;
    BaseItemType(String name) { this.name = name; }

    @Override public String getName() { return this.name; }
    @Override public boolean applyEffect() { return false; }
    @Override public String shortString() { return this.name.substring(0,1); }
}



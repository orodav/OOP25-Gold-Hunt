package it.unibo.goldhunt.items.api;
//luca
public interface CellContent{
    public boolean applyEffect();

    public String shortString();

    default boolean isTrap(){
        return false;
    }
}

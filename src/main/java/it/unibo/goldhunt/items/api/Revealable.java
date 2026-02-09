package it.unibo.goldhunt.items.api;


/**
 * A marker interface to eliminate depeandancies when
 * traps are checked
 * <p>
 * This interface does not declare any method and it
 * is used by {@code Trap} class to eliminate dependancies,   
 * 
 */
public interface Revealable extends CellContent{
    //marker
}

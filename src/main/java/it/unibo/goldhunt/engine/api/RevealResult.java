package main.java.it.unibo.goldhunt.engine.api;

//davv
public record RevealResult(
    boolean changed,
    ActionEffect effect,
    LevelState newLevelState
) { }

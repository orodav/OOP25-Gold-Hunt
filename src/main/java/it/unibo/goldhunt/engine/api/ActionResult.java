package main.java.it.unibo.goldhunt.engine.api;

//davv
public record ActionResult(
    ActionType type,
    StopReason reason,
    LevelState levelState,
    ActionEffect effect
) { }

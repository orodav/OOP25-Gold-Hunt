package it.unibo.goldhunt.engine.api;
//davv
public enum StopReason {
    REACHED_CELL,
    ALREADY_THERE,
    BLOCKED,
    NO_AVAILABLE_PATH,
    ON_WARNING,
    NONE;
}

/* NONE è per azioni che non riguardano il movimento, rendendo la 
enum utilizzabile anche in altri contesti */
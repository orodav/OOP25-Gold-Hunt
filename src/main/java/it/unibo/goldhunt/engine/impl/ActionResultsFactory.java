package it.unibo.goldhunt.engine.impl;

import it.unibo.goldhunt.engine.api.ActionEffect;
import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.engine.api.ActionType;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.engine.api.StopReason;

final class ActionResultsFactory {
    
    private ActionResultsFactory() {}

    static ActionResult move(
        final Status status,
        final StopReason reason,
        final ActionEffect effect
    ) {
        return new ActionResult(
            ActionType.MOVE,
            reason,
            status.levelState(),
            effect
        );
    }

    static ActionResult reveal(
        final Status status,
        final ActionEffect effect
    ) {
        return new ActionResult(
            ActionType.REVEAL,
            StopReason.NONE,
            status.levelState(),
            effect
        );
    }

    static ActionResult flag(
        final Status status,
        final ActionEffect effect
    ) {
        return new ActionResult(
            ActionType.FLAG,
            StopReason.NONE,
            status.levelState(),
            effect
        );
    }
}


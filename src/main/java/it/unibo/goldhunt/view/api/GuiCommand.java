package it.unibo.goldhunt.view.api;

import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.engine.api.Direction;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;

/**
 * Represents a user interacion issued from the GUI and handled from the GUI
 * by the GameController
 */
public interface GuiCommand {

    record MoveTo(Position pos) implements GuiCommand {}
    
    record Move(Direction dir) implements GuiCommand {}

    record Reveal(Position pos) implements GuiCommand {}

    record ToggleFlag(Position pos) implements GuiCommand {}

    record Buy(ItemTypes type) implements GuiCommand {}

    record LeaveShop() implements GuiCommand {}

    record NewGame(Difficulty difficulty) implements GuiCommand {}

    record Reset() implements GuiCommand {}
}

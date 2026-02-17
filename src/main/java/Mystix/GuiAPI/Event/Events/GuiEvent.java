package Mystix.GuiAPI.Event.Events;

import Mystix.GuiAPI.Event.Event;
import Mystix.GuiAPI.Gui.Gui;

public class GuiEvent implements Event {

    private final Gui gui;

    public GuiEvent(Gui gui) {
        this.gui = gui;
    }

    public Gui gui() {
        return gui;
    }
}
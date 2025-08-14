package Mystix.GuiAPI.Events;

import Mystix.GuiAPI.Gui.Gui;
import org.bukkit.entity.HumanEntity;

public class GuiCloseEvent extends GuiEvent {
    private final HumanEntity whoClosed;

    public GuiCloseEvent(Gui gui, HumanEntity whoClosed) {
        super(gui);
        this.whoClosed = whoClosed;
    }

    public HumanEntity getWhoClosed() {
        return whoClosed;
    }

}

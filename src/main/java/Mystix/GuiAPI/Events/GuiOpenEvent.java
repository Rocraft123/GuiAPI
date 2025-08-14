package Mystix.GuiAPI.Events;

import Mystix.GuiAPI.Gui.Gui;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Cancellable;

public class GuiOpenEvent extends GuiEvent implements Cancellable {

    private boolean cancelled = false;

    private final HumanEntity whoOpened;

    public GuiOpenEvent(Gui gui, HumanEntity whoOpened) {
        super(gui);
        this.whoOpened = whoOpened;
    }

    public HumanEntity getWhoOpened() {
        return whoOpened;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}

package Mystix.GuiAPI.Event.Events;

import Mystix.GuiAPI.Gui.Gui;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GuiCloseEvent extends GuiEvent implements Cancellable {

    private final Player viewer;
    private final InventoryCloseEvent.Reason reason;

    private boolean cancel = false;

    public GuiCloseEvent(Gui gui, Player viewer, InventoryCloseEvent.Reason reason) {
        super(gui);
        this.viewer = viewer;
        this.reason = reason;
    }

    public Player getViewer() {
        return viewer;
    }

    public InventoryCloseEvent.Reason getReason() {
        return reason;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}

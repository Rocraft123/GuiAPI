package Mystix.GuiAPI.Event.Events;

import Mystix.GuiAPI.Gui.Gui;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class GuiOpenEvent extends GuiEvent implements Cancellable {

    private boolean cancel = false;

    private final Player viewer;

    public GuiOpenEvent(Gui gui, Player viewer) {
        super(gui);
        this.viewer = viewer;
    }

    public Player getViewer() {
        return viewer;
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

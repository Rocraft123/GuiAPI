package Mystix.GuiAPI.Event.Events;

import Mystix.GuiAPI.Gui.Gui;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class GuiRenderEvent extends GuiEvent implements Cancellable {

    private boolean cancelled = false;

    private final Player player;

    public GuiRenderEvent(Gui gui, Player player) {
        super(gui);
        this.player = player;
    }

    public Player viewer() {
        return player;
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
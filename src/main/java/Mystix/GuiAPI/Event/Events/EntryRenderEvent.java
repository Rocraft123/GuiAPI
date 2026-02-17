package Mystix.GuiAPI.Event.Events;

import Mystix.GuiAPI.Gui.Entry;
import Mystix.GuiAPI.Gui.Gui;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class EntryRenderEvent extends EntryEvent implements Cancellable {

    private boolean cancelled = false;

    private final Gui gui;
    private final Player player;

    public EntryRenderEvent(Entry entry, Gui gui, Player player) {
        super(entry);
        this.gui = gui;
        this.player = player;
    }

    public Gui gui() {
        return gui;
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

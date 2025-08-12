package Mystix.Events;

import Mystix.Gui.Entry;
import Mystix.Gui.Gui;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Cancellable;

public class EntryRenderEvent extends EntryEvent implements Cancellable {

    private boolean cancelled = false;

    private final HumanEntity viewer;

    public EntryRenderEvent(Gui gui, Entry entry, HumanEntity viewer) {
        super(gui, entry);
        this.viewer = viewer;
    }

    public HumanEntity getViewer() {
        return viewer;
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

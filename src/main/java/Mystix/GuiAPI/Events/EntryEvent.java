package Mystix.GuiAPI.Events;

import Mystix.GuiAPI.Gui.Entry;
import Mystix.GuiAPI.Gui.Gui;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class EntryEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private final Gui gui;
    private final Entry entry;

    public EntryEvent(Gui gui, Entry entry) {
        this.gui = gui;
        this.entry = entry;
    }

    public Gui getGui() {
        return gui;
    }

    public Entry getEntry() {
        return entry;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlerList;
    }
}

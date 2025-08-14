package Mystix.GuiAPI.Events;

import Mystix.GuiAPI.Gui.Gui;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GuiEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private final Gui gui;

    public GuiEvent(Gui gui) {
        this.gui = gui;
    }

    public Gui getGui() {
        return gui;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlerList;
    }
}

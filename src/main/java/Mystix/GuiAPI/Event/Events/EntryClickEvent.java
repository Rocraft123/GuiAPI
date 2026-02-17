package Mystix.GuiAPI.Event.Events;

import Mystix.GuiAPI.Gui.Entry;
import Mystix.GuiAPI.Gui.Gui;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

public class EntryClickEvent extends EntryEvent implements Cancellable {

    private boolean cancelled = false;

    private final Gui gui;
    private final Player player;
    private final int slot;
    private final InventoryAction action;
    private final ClickType clickType;

    public EntryClickEvent(Entry entry, Gui gui, Player player, int slot, InventoryAction action, ClickType clickType) {
        super(entry);
        this.gui = gui;
        this.player = player;
        this.slot = slot;
        this.action = action;
        this.clickType = clickType;
    }

    public Gui gui() {
        return gui;
    }

    public Player player() {
        return player;
    }

    public int slot() {
        return slot;
    }

    public InventoryAction action() {
        return action;
    }

    public ClickType click() {
        return clickType;
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

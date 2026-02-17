package Mystix.GuiAPI.Event.Events;

import Mystix.GuiAPI.Gui.Entry;
import Mystix.GuiAPI.Gui.Gui;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

public class GuiClickEvent extends GuiEvent implements Cancellable {

    private boolean cancelled = false;

    private final Entry entry;
    private final Player player;
    private final int slot;
    private final InventoryAction action;
    private final ClickType clickType;

    public GuiClickEvent(Gui gui, Entry entry, Player player, int slot, InventoryAction action, ClickType clickType) {
        super(gui);
        this.entry = entry;
        this.player = player;
        this.slot = slot;
        this.action = action;
        this.clickType = clickType;
    }

    public Entry entry() {
        return entry;
    }

    public Player player() {
        return player;
    }

    public int getSlot() {
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
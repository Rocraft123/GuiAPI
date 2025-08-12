package Mystix.Events;

import Mystix.Gui.Entry;
import Mystix.Gui.Gui;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

public class EntryClickEvent extends EntryEvent implements Cancellable {
    private boolean canceled = false;
    private final ClickType click;
    private final InventoryAction action;
    private final HumanEntity whoClicked;
    private final int slot;

    public EntryClickEvent(Gui gui, Entry entry, ClickType click, InventoryAction action, HumanEntity whoClicked, int slot) {
        super(gui, entry);
        this.click = click;
        this.action = action;
        this.whoClicked = whoClicked;
        this.slot = slot;
    }

    public ClickType getClick() {
        return click;
    }

    public InventoryAction getAction() {
        return action;
    }

    public HumanEntity getWhoClicked() {
        return whoClicked;
    }

    public int getSlot() {
        return slot;
    }

    @Override
    public boolean isCancelled() {
        return canceled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.canceled = cancel;
    }
}

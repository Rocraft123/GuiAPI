package Mystix.GuiAPI.Gui;

import Mystix.GuiAPI.Events.EntryClickEvent;
import Mystix.GuiAPI.Events.EntryRenderEvent;
import Mystix.GuiAPI.Events.GuiCloseEvent;
import Mystix.GuiAPI.Events.GuiOpenEvent;
import Mystix.GuiAPI.Exceptions.InvalidGuiSizeException;
import Mystix.GuiAPI.Functions.EntryClickFunction;
import Mystix.GuiAPI.Functions.GuiCloseFunction;
import Mystix.GuiAPI.Functions.GuiOpenFunction;
import Mystix.GuiAPI.Gui.History.GuiHistory;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a GUI with a fixed size or inventory type.
 * You can add, set, and remove {@link Entry} objects to populate this GUI.
 * Use {@link GuiBuilder} for easier construction.
 */
public class Gui {

    private final List<Entry> entries = new ArrayList<>();
    private int size;
    private Component title;
    private InventoryType type;

    private GuiOpenFunction onOpen;
    private GuiCloseFunction onClose;
    private EntryClickFunction onClick;

    private boolean debug = true;
    private boolean cancelAllClicks = false;

    /**
     * Constructs a new GUI with a chest-like size (must be a multiple of 9).
     *
     * @param title The GUI title (component, supports color).
     * @param size  The size of the inventory.
     * @throws IllegalArgumentException if size is not a multiple of 9.
     */
    public Gui(Component title, int size) {
        if (size % 9 != 0)
            throw new InvalidGuiSizeException(size);

        this.title = title;
        this.size = size;

        for (int i = 0; i < size; i++) {
            entries.add(null);
        }
    }

    /**
     * Constructs a GUI using a Bukkit inventory type (e.g., HOPPER, ANVIL).
     *
     * @param title The GUI title.
     * @param type  The inventory type.
     */
    public Gui(Component title, InventoryType type) {
        this.title = title;
        this.type = type;
    }

    public void setEntry(int slot, Entry entry) {
        this.entries.set(slot, entry);
    }

    public Entry getEntry(int slot) {
        return entries.get(slot);
    }

    public List<Entry> getEntriesByTag(String tag) {
        List<Entry> entries = new ArrayList<>();
        for (Entry entry : this.entries)
            if (entry.hasTag(tag)) entries.add(entry);

        return entries;
    }

    public void remove(int slot) {
        this.entries.set(slot, null);
    }

    public void remove(Entry entry) {
        this.entries.removeIf(entry::equals);
    }

    public void clearEntries() {
        this.entries.clear();
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public int getFirstEmpty() {
        int size = this.size;
        if (type != null) size = Bukkit.createInventory(null, type).getSize();

        for (int i = 0; i < size; i++) {
            if (getEntry(i) == null) return i;
        }

        return -1;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Component getTitle() {
        return title;
    }

    public void setTitle(Component title) {
        this.title = title;
    }

    public InventoryType getType() {
        return type;
    }

    public void setType(InventoryType type) {
        this.type = type;
    }

    public void setOnOpen(GuiOpenFunction onOpen) {
        this.onOpen = onOpen;
    }

    public void setOnClose(GuiCloseFunction onClose) {
        this.onClose = onClose;
    }
    public void setOnClick(EntryClickFunction onClick) {
        this.onClick = onClick;
    }

    public boolean isDebugEnabled() {
        return debug;
    }

    public void enableDebugging(boolean debug) {
        this.debug = debug;
    }

    public boolean cancelsAllClicks() {
        return cancelAllClicks;
    }

    public void cancelAllClicks(boolean cancelAllClicks) {
        this.cancelAllClicks = cancelAllClicks;
    }

    public void callOnOpen(GuiOpenEvent event) {
        if (onOpen != null) onOpen.execute(event);
    }

    public void callOnClose(GuiCloseEvent event) {
        if (onClose != null) onClose.execute(event);
    }

    public void callOnClick(EntryClickEvent event) {
        if (cancelAllClicks) event.setCancelled(true);
        if (onClick != null) onClick.execute(event);
    }

    /**
     * Opens the GUI to a player.
     *
     * @param player The player to open the GUI for.
     * @return The created inventory.
     */
    public Inventory open(Player player) {
        Inventory inventory = Bukkit.createInventory(player, size, title);
        if (type != null)
            inventory = Bukkit.createInventory(player, type, title);

        GuiOpenEvent openEvent = new GuiOpenEvent(this, player);
        this.callOnOpen(openEvent);
        Bukkit.getPluginManager().callEvent(openEvent);

        if (openEvent.isCancelled()) return null;

        for (int i = 0; i < inventory.getSize(); i++) {
            Entry entry = entries.get(i);
            if (entry == null) continue;

            EntryRenderEvent event = new EntryRenderEvent(this, entry, player);
            entry.callOnRender(event);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) continue;

            inventory.setItem(i, entry.getItem());
        }

        Gui current = GuiManager.getCurrentGui(player);
        if (current != null && current != this) {
            GuiHistory.push(player, current);
        }

        GuiManager.addOpenGui(inventory, this);
        player.openInventory(inventory);
        return inventory;
    }
}

package Mystix.GuiAPI.Gui;

import Mystix.GuiAPI.Event.Events.EntryRenderEvent;
import Mystix.GuiAPI.Event.Events.GuiOpenEvent;
import Mystix.GuiAPI.Event.Events.GuiRenderEvent;
import Mystix.GuiAPI.Gui.Builders.EntryBuilder;
import Mystix.GuiAPI.Gui.Builders.GuiBuilder;
import Mystix.GuiAPI.Gui.Flags.Flag;
import Mystix.GuiAPI.InitializedGuiAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

import java.util.*;
/**
 * Represents a mutable, inventory-backed graphical user interface (GUI).
 *
 * <p>
 * A {@code Gui} is composed of a fixed-size grid of slots (always a multiple of 9),
 * each of which may contain an {@link Entry}. The GUI can be opened for a {@link Player},
 * rendered dynamically via events, and refreshed while open. </p>
 *
 *
 * <h2>Lifecycle</h2>
 * <ol>
 *   <li>Create a {@code Gui} with a title and size</li>
        *   <li>Populate it with {@link Entry} instances</li>
        *   <li>Open it for a player using {@link #open(Player)}</li>
        *   <li>Optionally refresh it using {@link #refresh(Player)}</li>
 * </ol>
 *
 * <h2>Size constraints</h2>
 * <p>
 * The GUI size must always be a multiple of 9, matching Minecraft inventory row
 * requirements. Invalid sizes will result in an {@link IllegalArgumentException}.
        * </p>
        *
        * <h2>Flags</h2>
        * <p>
 * Flags provide extensible, type-safe metadata attached to the GUI. They can be
 * queried by internal systems, renderers, or event listeners.
 * </p>
 * <h2>Example</h2>
 * <pre>{@code
 *Gui gui = new Gui(Component.text("example"), 54, api);

 *gui.addEntry(new Entry(Material.DIAMOND));

 *gui.open(player);
 * }</pre>
 * @see Entry
 * @see Mystix.GuiAPI.Gui.Builders.GuiBuilder
 *
 * @since 0.1.0-alpha
 */
public class Gui {

    private final Map<Flag<?>, Object> flags = new HashMap<>();

    private final List<Entry> entries = new ArrayList<>();
    private final InitializedGuiAPI api;

    private int size;
    private Component title;
    private GuiBuilder builder;

    /**
     * Creates a new GUI.
     *
     * @param title the title displayed at the top of the inventory
     * @param size the size of the GUI (must be a multiple of 9)
     * @param api the initialized GUI API instance
     *
     * @throws IllegalArgumentException if {@code size} is not a multiple of 9
     */
    public Gui(Component title, int size, InitializedGuiAPI api) {
        if (size % 9 != 0)
            throw new IllegalArgumentException("GUI size must be a multiple of 9");

        this.api = api;
        this.title = title;
        this.size = size;

        for (int i = 0; i < size; i++)
            entries.add(null);
    }

    /**
     * Sets an entry at a specific slot.
     *
     * @param slot the slot index
     * @param entry the entry to place, or {@code null} to clear the slot
     *
     * @throws IndexOutOfBoundsException if the slot is outside the GUI bounds
     */
    public void setEntry(int slot, Entry entry) {
        this.entries.set(slot, entry);
    }


    /**
     * Adds an entry to the first available empty slot.
     *
     * @param entry the entry to add
     * @return {@code true} if the entry was added, {@code false} if the GUI is full
     */
    public boolean addEntry(Entry entry) {
        if (getFirstEmpty() == -1) return false;

        this.entries.set(getFirstEmpty(), entry);
        return true;
    }

    /**
     * Retrieves the entry at a given slot.
     *
     * @param slot the slot index
     * @return the entry at the slot, or {@code null} if empty
     */
    public Entry getEntry(int slot) {
        return entries.get(slot);
    }

    /**
     * Removes the entry at a given slot.
     *
     * @param slot the slot index
     * @return {@code true} if an entry was removed, {@code false} if the slot was empty
     */
    public boolean remove(int slot) {
        if (entries.get(slot) == null) return false;

        this.entries.set(slot, null);
        return true;
    }

    /**
     * Removes all occurrences of the given entry from this GUI.
     *
     * @param entry the entry to remove
     */
    public void remove(Entry entry) {
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i) != null && entries.get(i).equals(entry))
                entries.set(i, null);
        }
    }

    /**
     * Clears all entries from the GUI.
     */
    public void clear() {
        for (int i = 0; i < size; i++)
            entries.set(i, null);
    }
    /**
     * Returns the backing entry list.
     *
     * @return the list of entries (unmodifiable)
     */
    public List<Entry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    /**
     * Finds the first empty slot.
     *
     * @return the slot index, or {@code -1} if the GUI is full
     */
    public int getFirstEmpty() {
        for (int i = 0; i < size; i++)
            if (getEntry(i) == null) return i;

        return -1;
    }


    /**
     * Returns the GUI size.
     *
     * @return the number of slots
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the GUI size.
     *
     * @param size the new size (must be a multiple of 9)
     *
     * @throws IllegalArgumentException if {@code size} is not a multiple of 9
     */
    public void setSize(int size) {
        if (size % 9 != 0)
            throw new IllegalArgumentException("GUI size must be a multiple of 9");

        this.size = size;
    }

    /**
     * Returns the GUI title component.
     *
     * @return the title
     */
    public @NotNull Component getTitle() {
        return title;
    }

    /**
     * Sets the GUI title.
     *
     * @param title the new title
     */
    public void setTitle(@NotNull Component title) {
        this.title = title;
    }

    /**
     * Sets a typed flag value.
     *
     * @param flag the flag key
     * @param value the flag value
     * @param <T> the flag value type
     *
     * @throws IllegalArgumentException if the value type does not match the flag
     *
     * @see Mystix.GuiAPI.Gui.Flags.GuiFlags
     */
    public <T> void setFlag(Flag<T> flag, T value) {
        if (!flag.getType().isInstance(value))
            throw new IllegalArgumentException("Invalid type for flag " + flag.getKey());

        flags.put(flag, value);
    }

    /**
     * Returns all flags applied to this GUI.
     *
     * @return an unmodifiable flag map
     */
    public Map<Flag<?>, Object> getFlags() {
        return Collections.unmodifiableMap(flags);
    }


    /**
     * Retrieves a flag value or its default.
     *
     * @param flag the flag key
     * @param <T> the flag value type
     * @return the stored or default value
     */
    public <T> T getFlag(Flag<T> flag) {
        return flag.getType().cast(flags.getOrDefault(flag, flag.getDefaultValue()));
    }

    /**
     * Checks whether a flag is explicitly set.
     *
     * @param flag the flag key
     * @return {@code true} if present
     */
    public boolean hasFlag(Flag<?> flag) {
        return flags.containsKey(flag);
    }

    /**
     * Creates a deep copy of this GUI.
     *
     * @return a cloned GUI instance
     */
    public Gui copy() {
        Gui clone = new Gui(title, size, api);
        for (int i = 0; i < size; i++) {
            Entry entry = this.entries.get(i);
            if (entry != null)
                clone.setEntry(i, entry.clone());
        }
        clone.flags.putAll(this.flags);
        return clone;
    }

    /**
     * Sets the builder that was used to create the gui.
     * @param builder builder :D
     */
    public void attachBuilder(GuiBuilder builder) {
        this.builder = builder;
    }
    /**
     * @return The builder that was used to create the gui.
     */
    public GuiBuilder getAttached() {
        return builder;
    }

    /**
     * Opens this GUI for a player.
     *
     * <p>
     * Triggers render and open events before displaying the inventory.
     * </p>
     *
     * @param player the player to open the GUI for
     * @return the created inventory
     */
    public Inventory open(Player player) {
        Inventory inventory = Bukkit.createInventory(new GuiHolder(this), size, title);

        GuiRenderEvent event = new GuiRenderEvent(this, player);
        api.getEventManager().callEvent(event);
        if (!event.isCancelled()) render(inventory, player);

        GuiOpenEvent openEvent = new GuiOpenEvent(this, player);
        if (openEvent.isCancelled()) return inventory;

        player.openInventory(inventory);
        return inventory;
    }

    private void render(Inventory inventory, Player player) {
        inventory.clear();
        for (int i = 0; i < inventory.getSize(); i++) {
            Entry entry = entries.get(i);
            if (entry == null) continue;

            EntryRenderEvent event = new EntryRenderEvent(entry, this, player);
            api.getEventManager().callEvent(event);

            if (!event.isCancelled())
                inventory.setItem(i, entry.getItem());
        }
    }

    /**
     * Re-renders the GUI for a player if it is currently open.
     *
     * @param player the player viewing the GUI
     */
    public void refresh(Player player) {
        InventoryView view = player.getOpenInventory();
        if (!(view.getTopInventory().getHolder() instanceof GuiHolder(Gui gui))) return;
        if (!gui.equals(this)) return;
        render(view.getTopInventory(), player);
    }

    /**
     * Creates an empty 6-row GUI with no title.
     *
     * @param api the initialized GUI API
     * @return a new empty GUI
     */
    public static Gui empty(InitializedGuiAPI api) {
        return new Gui(Component.text(""), 54, api);
    }
}

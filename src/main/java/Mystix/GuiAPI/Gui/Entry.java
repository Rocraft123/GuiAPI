package Mystix.GuiAPI.Gui;

import Mystix.GuiAPI.Gui.Builders.EntryBuilder;
import Mystix.GuiAPI.Gui.Flags.Flag;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
/**
 * Represents a single GUI entry backed by an {@link ItemStack}.
 *
 * <p>
 * An {@code Entry} defines both the visual item displayed in a GUI slot and
 * optional metadata via {@link Flag Flags}. Entries are mutable, cloneable,
 * and intended to be rendered by a {@link Gui}.
 * </p>
 *
 * <h2>Item safety</h2>
 * <p>
 * All {@link ItemStack} access is defensive:
 * internal items are cloned on assignment and retrieval to prevent external
 * mutation.
 * </p>
 *
 * <h2>Flags</h2>
 * <p>
 * Flags provide extensible, type-safe metadata attached to an entry. They are
 * commonly used by renderers, click handlers, or higher-level GUI logic.
 * </p>
 *
 * <h2>Builders</h2>
 * <p>
 * An entry may be associated with an {@link EntryBuilder}. This relationship
 * is optional and primarily used internally to track the source or lifecycle
 * of the entry.
 * </p>
 *
 * <h2>Example</h2>
 * <pre>{@code
 * Entry entry = new Entry(Material.DIAMOND);
 *
 * entry.editItemMeta(meta -> {
 *     meta.displayName(Component.text("Click me"));
 *     return meta;
 * });
 *
 * entry.setFlag(flag, value);
 * }</pre>
 *
 * @see Gui
 * @see EntryBuilder
 * @see Flag
 * @see Mystix.GuiAPI.Gui.Flags.EntryFlags
 *
 * @since 0.1.0-alpha
 */
public class Entry implements Cloneable {

    private ItemStack item;
    private EntryBuilder builder;
    private final Map<Flag<?>, Object> flags = new HashMap<>();

    /**
     * Creates a new entry using a cloned item stack.
     *
     * @param item the item stack to display
     *
     * @throws NullPointerException if {@code item} is {@code null}
     */
    public Entry(ItemStack item) {
        this.item = item.clone();
    }

    /**
     * Creates a new entry with a single item of the given material.
     *
     * @param material the item material
     */
    public Entry(Material material) {
        this.item = new ItemStack(material);
    }

    /**
     * Creates a new entry with the given material and amount.
     *
     * @param material the item material
     * @param amount the item amount
     */
    public Entry(Material material, int amount) {
        this.item = new ItemStack(material, amount);
    }

    /**
     * Returns a cloned copy of the backing item.
     *
     * @return a defensive copy of the item stack
     */
    public ItemStack getItem() {
        return item.clone();
    }

    /**
     * Sets the backing item.
     *
     * @param item the new item, or {@code null} to clear it
     */
    public void setItem(ItemStack item) {
        this.item = item == null ? null : item.clone();
    }

    /**
     * Modifies the {@link ItemMeta} of the backing item.
     *
     * <p>
     * The provided function receives the current meta and must return
     * the modified meta instance.
     * </p>
     *
     * @param function a transformation function for the item meta
     *
     * @throws NullPointerException if {@code function} is {@code null}
     */
    public void editItemMeta(Function<ItemMeta, ItemMeta> function) {
        item.setItemMeta(function.apply(item.getItemMeta()));
    }

    /**
     * Sets a typed flag value on this entry.
     *
     * @param flag the flag key
     * @param value the flag value
     * @param <T> the flag value type
     *
     * @throws IllegalArgumentException if the value type does not match the flag
     *
     * @see Gui#setFlag(Flag, Object)
     * @see Flag
     */
    public <T> void setFlag(Flag<T> flag, T value) {
        if (!flag.getType().isInstance(value))
            throw new IllegalArgumentException("Invalid type for flag " + flag.getKey());

        flags.put(flag, value);
    }

    /**
     * Returns all flags applied to this entry.
     *
     * @return an unmodifiable view of the flag map
     */
    public Map<Flag<?>, Object> getFlags() {
        return Collections.unmodifiableMap(flags);
    }

    /**
     * Retrieves a flag value or its default.
     *
     * @param flag the flag key
     * @param <T> the flag value type
     * @return the stored value or the flag default
     */
    public <T> T getFlag(Flag<T> flag) {
        return flag.getType().cast(flags.getOrDefault(flag, flag.getDefaultValue()));
    }

    /**
     * Checks whether a flag is explicitly set.
     *
     * @param flag the flag key
     * @return {@code true} if the flag is present
     */
    public boolean hasFlag(Flag<?> flag) {
        return flags.containsKey(flag);
    }

    /**
     * Attaches an {@link EntryBuilder} to this entry.
     *
     * @param builder the builder to attach
     * @apiNote Intended for internal use
     */
    public void attachBuilder(EntryBuilder builder) {
        this.builder = builder;
    }

    /**
     *
     * @return the builder that was used to create the entry, or {@code null}
     */
    public EntryBuilder getAttached() {
        return builder;
    }

    /**
     * Creates a deep copy of this entry.
     *
     * <p>
     * The cloned entry receives:
     * <ul>
     *   <li>a cloned {@link ItemStack}</li>
     *   <li>a copy of all flags</li>
     * </ul>
     * The attached builder is not copied.
     * </p>
     *
     * @return a cloned entry
     */
    @Override
    public Entry clone() {
        Entry entry = new Entry(item);
        entry.flags.putAll(this.getFlags());
        return entry;
    }
}
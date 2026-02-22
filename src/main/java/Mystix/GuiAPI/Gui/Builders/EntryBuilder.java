package Mystix.GuiAPI.Gui.Builders;

import Mystix.GuiAPI.Event.Events.EntryClickEvent;
import Mystix.GuiAPI.Event.Events.EntryRenderEvent;
import Mystix.GuiAPI.Gui.Entry;
import Mystix.GuiAPI.Gui.Flags.EntryFlags;
import Mystix.GuiAPI.Gui.Flags.Flag;
import Mystix.GuiAPI.Gui.Gui;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Fluent builder for {@link Entry} instances.
 * <p>
 * Provides utilities for:
 * <ul>
 *     <li>Item metadata modification</li>
 *     <li>Click and render handlers</li>
 *     <li>Permissions and navigation</li>
 *     <li>Custom flag manipulation</li>
 * </ul>
 *
 * <p>All methods return the builder for fluent chaining.</p>
 */
public class EntryBuilder implements Builder<Entry> {

    /** Backing entry instance being configured. */
    protected final Entry entry;

    /**
     * Creates a builder from an existing item.
     *
     * @param item base item stack
     */
    public EntryBuilder(ItemStack item) {
        this.entry = new Entry(item);
    }

    /**
     * Creates a builder from the selected material.
     *
     * @param item material type
     */
    public EntryBuilder(Material item) {
        this.entry = new Entry(item);
    }

    /**
     * Creates a builder from the selected material and amount.
     *
     * @param item   material type
     * @param amount initial amount
     */
    public EntryBuilder(Material item, int amount) {
        this.entry = new Entry(item, amount);
    }

    /**
     * Replaces the underlying item stack.
     *
     * @param item new item stack
     * @return this builder
     */
    public EntryBuilder setItem(ItemStack item) {
        this.entry.setItem(item);
        return this;
    }

    /**
     * Sets the display name.
     *
     * @param name component name
     * @return this builder
     */
    public EntryBuilder setName(Component name) {
        this.entry.editItemMeta(meta -> {
            meta.displayName(name);
            return meta;
        });
        return this;
    }

    /**
     * Sets the lore.
     *
     * @param lore lore components
     * @return this builder
     */
    public EntryBuilder setLore(List<Component> lore) {
        this.entry.editItemMeta(meta -> {
            meta.lore(lore);
            return meta;
        });
        return this;
    }

    /**
     * Enables or disables the glowing effect.
     *
     * @param glowing true to enable glow
     * @return this builder
     */
    public EntryBuilder setGlowing(boolean glowing) {
        this.entry.editItemMeta(meta -> {
            if (glowing) {
                meta.addEnchant(Enchantment.LURE, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_STORED_ENCHANTS);
            } else {
                for (Enchantment ench : meta.getEnchants().keySet()) {
                    meta.removeEnchant(ench);
                }
                meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_STORED_ENCHANTS);
            }
            return meta;
        });
        return this;
    }

    /**
     * Sets the stack amount.
     *
     * @param amount new amount
     * @return this builder
     */
    public EntryBuilder setAmount(int amount) {
        ItemStack item = this.entry.getItem();
        item.setAmount(amount);
        this.entry.setItem(item);
        return this;
    }

    /**
     * Sets the click handler.
     *
     * @param eventConsumer click consumer
     * @return this builder
     */
    public EntryBuilder setOnClick(Consumer<EntryClickEvent> eventConsumer) {
        this.entry.setFlag(EntryFlags.ON_CLICK, eventConsumer);
        return this;
    }

    /**
     * Sets the render handler.
     *
     * @param eventConsumer render consumer
     * @return this builder
     */
    public EntryBuilder setOnRender(Consumer<EntryRenderEvent> eventConsumer) {
        this.entry.setFlag(EntryFlags.ON_RENDER, eventConsumer);
        return this;
    }

    /**
     * Controls whether the click event should be canceled.
     *
     * @param cancel true to cancel clicks
     * @return this builder
     */
    public EntryBuilder cancelClick(boolean cancel) {
        this.entry.setFlag(EntryFlags.CANCEL, cancel);
        return this;
    }

    /**
     * Sets the sound played when the entry is clicked.
     *
     * @param sound click sound
     * @return this builder
     */
    public EntryBuilder clickSound(Sound sound) {
        this.entry.setFlag(EntryFlags.CLICK_SOUND, sound);
        return this;
    }

    /**
     * Sets the message sent on click.
     *
     * @param message message component
     * @return this builder
     */
    public EntryBuilder clickMessage(Component message) {
        this.entry.setFlag(EntryFlags.MESSAGE, message);
        return this;
    }

    /**
     * Sets the required permission to use this entry.
     *
     * @param permission permission node
     * @return this builder
     */
    public EntryBuilder setPermission(String permission) {
        this.entry.setFlag(EntryFlags.PERMISSION, permission);
        return this;
    }

    /**
     * Sets the message shown when permission is missing.
     *
     * @param message denial message
     * @return this builder
     */
    public EntryBuilder setPermissionMessage(Component message) {
        this.entry.setFlag(EntryFlags.PERMISSION_MSG, message);
        return this;
    }

    /**
     * Sets the GUI to open when this entry is clicked.
     *
     * @param gui target GUI
     * @return this builder
     */
    public EntryBuilder setGuiTo(Gui gui) {
        this.entry.setFlag(EntryFlags.NAVIGATE_TO, gui);
        return this;
    }

    /**
     * Adds a command to execute on click.
     *
     * @param command command string
     * @return this builder
     */
    public EntryBuilder addCommand(String command) {
        if (this.entry.hasFlag(EntryFlags.COMMANDS))
            this.entry.getFlag(EntryFlags.COMMANDS).add(command);
        else this.entry.setFlag(EntryFlags.COMMANDS, List.of(command));
        return this;
    }

    /**
     * Adds multiple commands to execute on click.
     *
     * @param commands command list
     * @return this builder
     */
    public EntryBuilder addCommands(List<String> commands) {
        List<String> existing = this.entry.hasFlag(EntryFlags.COMMANDS)
                ? new ArrayList<>(this.entry.getFlag(EntryFlags.COMMANDS))
                : new ArrayList<>();
        existing.addAll(commands);
        this.entry.setFlag(EntryFlags.COMMANDS, existing);
        return this;
    }

    /**
     * Edits the item meta using a custom function.
     *
     * @param newMeta meta transformer
     * @return this builder
     */
    public EntryBuilder editItemMeta(Function<ItemMeta, ItemMeta> newMeta) {
        entry.editItemMeta(newMeta);
        return this;
    }

    /**
     * Sets the click cooldown in ticks.
     *
     * @param cooldown cooldown duration
     * @return this builder
     */
    public EntryBuilder setCooldown(int cooldown) {
        this.entry.setFlag(EntryFlags.COOLDOWN, cooldown);
        return this;
    }

    /**
     * Applies additional builder logic.
     *
     * @param builderConsumer consumer receiving this builder
     * @return this builder
     */
    public EntryBuilder function(Consumer<EntryBuilder> builderConsumer) {
        builderConsumer.accept(this);
        return this;
    }

    /**
     * Sets a custom flag value.
     *
     * @param flag  flag key
     * @param value flag value
     * @param <T>   flag type
     * @return this builder
     */
    public <T> EntryBuilder setFlag(Flag<T> flag, T value) {
        this.entry.setFlag(flag, value);
        return this;
    }

    /**
     * Builds the entry.
     *
     * @return built entry
     */
    @Override
    public Entry build() {
        entry.attachBuilder(this);
        return this.entry;
    }
}
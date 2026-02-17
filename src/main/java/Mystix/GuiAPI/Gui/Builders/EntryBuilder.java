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

public class EntryBuilder implements Builder<Entry> {

    protected final Entry entry;

    public EntryBuilder(ItemStack item) {
        this.entry = new Entry(item);
    }

    public EntryBuilder(Material item) {
        this.entry = new Entry(item);
    }

    public EntryBuilder(Material item, int amount) {
        this.entry = new Entry(item, amount);
    }

    public EntryBuilder setItem(ItemStack item) {
        this.entry.setItem(item);
        return this;
    }

    public EntryBuilder setName(Component name) {
        this.entry.editItemMeta(meta -> {
            meta.displayName(name);
            return meta;
        });
        return this;
    }

    public EntryBuilder setLore(List<Component> lore) {
        this.entry.editItemMeta(meta -> {
            meta.lore(lore);
            return meta;
        });
        return this;
    }

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

    public EntryBuilder setAmount(int amount) {
        ItemStack item = this.entry.getItem();
        item.setAmount(amount);
        this.entry.setItem(item);
        return this;
    }

    public EntryBuilder setOnClick(Consumer<EntryClickEvent> eventConsumer) {
        this.entry.setFlag(EntryFlags.ON_CLICK, eventConsumer);
        return this;
    }

    public EntryBuilder setOnRender(Consumer<EntryRenderEvent> eventConsumer) {
        this.entry.setFlag(EntryFlags.ON_RENDER, eventConsumer);
        return this;
    }

    public EntryBuilder cancelClick(boolean cancel) {
        this.entry.setFlag(EntryFlags.CANCEL, cancel);
        return this;
    }

    public EntryBuilder clickSound(Sound sound) {
        this.entry.setFlag(EntryFlags.CLICK_SOUND, sound);
        return this;
    }

    public EntryBuilder clickMessage(Component message) {
        this.entry.setFlag(EntryFlags.MESSAGE, message);
        return this;
    }

    public EntryBuilder setPermission(String permission) {
        this.entry.setFlag(EntryFlags.PERMISSION, permission);
        return this;
    }

    public EntryBuilder setPermissionMessage(Component message) {
        this.entry.setFlag(EntryFlags.PERMISSION_MSG, message);
        return this;
    }

    public EntryBuilder setGuiTo(Gui gui) {
        this.entry.setFlag(EntryFlags.NAVIGATE_TO, gui);
        return this;
    }

    public EntryBuilder addCommand(String command) {
        if (this.entry.hasFlag(EntryFlags.COMMANDS))
            this.entry.getFlag(EntryFlags.COMMANDS).add(command);
        else this.entry.setFlag(EntryFlags.COMMANDS, List.of(command));
        return this;
    }

    public EntryBuilder addCommands(List<String> commands) {
        List<String> existing = this.entry.hasFlag(EntryFlags.COMMANDS)
                ? new ArrayList<>(this.entry.getFlag(EntryFlags.COMMANDS))
                : new ArrayList<>();
        existing.addAll(commands);
        this.entry.setFlag(EntryFlags.COMMANDS, existing);
        return this;
    }

    public EntryBuilder editItemMeta(Function<ItemMeta, ItemMeta> newMeta) {
        entry.editItemMeta(newMeta);
        return this;
    }

    public EntryBuilder setCooldown(int cooldown) {
        this.entry.setFlag(EntryFlags.COOLDOWN, cooldown);
        return this;
    }

    public EntryBuilder function(Consumer<EntryBuilder> builderConsumer) {
        builderConsumer.accept(this);
        return this;
    }

    public <T> EntryBuilder setFlag(Flag<T> flag, T value) {
        this.entry.setFlag(flag, value);
        return this;
    }

    @Override
    public Entry build() {
        entry.attachBuilder(this);
        return this.entry;
    }
}
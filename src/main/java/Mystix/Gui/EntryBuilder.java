package Mystix.Gui;

import Mystix.Functions.EntryClickFunction;
import Mystix.Functions.EntryRenderFunction;
import Mystix.Gui.History.GuiHistory;
import Mystix.Pack.Models.APIFunction;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class EntryBuilder {

    private final Entry entry;

    public EntryBuilder(ItemStack item) {
        this.entry = new Entry(item);
    }

    public EntryBuilder(Material material, int amount) {
        this.entry = new Entry(material, amount);
    }

    public EntryBuilder setItem(ItemStack item) {
        this.entry.setItem(item);
        return this;
    }

    public EntryBuilder setOnClick(EntryClickFunction onClick) {
        this.entry.setOnClick(onClick);
        return this;
    }

    public EntryBuilder setOnRender(EntryRenderFunction onRender) {
        this.entry.setOnRender(onRender);
        return this;
    }

    public Entry build() {
        return this.entry;
    }

    public EntryBuilder addTag(String tag) {
        this.entry.addTag(tag);
        return this;
    }

    public EntryBuilder addCommand(String command) {
        this.entry.addCommand(command);
        return this;
    }

    public EntryBuilder setRenderIf(Predicate<Player> renderIf) {
        this.entry.setRenderIf(renderIf);
        return this;
    }

    public EntryBuilder editMeta(Consumer<ItemMeta> editor) {
        ItemStack item = this.entry.getItem();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            editor.accept(meta);
            item.setItemMeta(meta);
        }
        this.entry.setItem(item);
        return this;
    }

    public EntryBuilder setMetaData(NamespacedKey key, Object value) {
        this.entry.setMetaData(key, value);
        return this;
    }

    public static EntryBuilder of(ItemStack item) {
        return new EntryBuilder(item);
    }

    public static EntryBuilder of(Material material, int amount) {
        return new EntryBuilder(material, amount);
    }
}

package Mystix.GuiAPI.Gui;

import Mystix.GuiAPI.Events.EntryClickEvent;
import Mystix.GuiAPI.Events.EntryRenderEvent;
import Mystix.GuiAPI.Functions.EntryClickFunction;
import Mystix.GuiAPI.Functions.EntryRenderFunction;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

/**
 * Represents a clickable and optionally permission-restricted item in a GUI.
 * Supports onClick and onRender behavior, also see
 * {@link EntryRenderEvent} and {@link EntryClickEvent} for more info on these events.
 */
public class Entry {

    private ItemStack item;

    private EntryClickFunction onClick;
    private EntryRenderFunction onRender;
    private Predicate<Player> renderIf;

    private final List<String> tags = new ArrayList<>();
    private final List<String> commands = new ArrayList<>();
    private final HashMap<NamespacedKey, Object> metaData = new HashMap<>();

    /**
     * Constructs an Entry using a full item stack.
     * Can be usefully for complex items with custom item meta.
     * @param item ItemStack to represent this entry.
     */
    public Entry(ItemStack item) {
        this.item = item.clone();
    }

    /**
     * Constructs an Entry using a material and amount.
     * Can be usefully for Simple items.
     * @param material Material of the item.
     * @param amount   Quantity.
     */
    public Entry(Material material, int amount) {
        this.item = new ItemStack(material, amount);
    }

    public ItemStack getItem() {
        return item.clone();
    }

    public void setItem(ItemStack item) {
        this.item = item == null ? null : item.clone();
    }

    public void setOnClick(EntryClickFunction onClick) {
        this.onClick = onClick;
    }

    public EntryClickFunction getOnClick() {
        return onClick;
    }

    public void setOnRender(EntryRenderFunction onRender) {
        this.onRender = onRender;
    }

    public EntryRenderFunction getOnRender() {
        return onRender;
    }

    public void setRenderIf(Predicate<Player> renderIf) {
        this.renderIf = renderIf;
    }

    public void callOnClick(EntryClickEvent event) {
        HumanEntity entity = event.getWhoClicked();
        for (String command : commands) {
            String newCommand = command.replace("{player}", entity.getUniqueId().toString());
            if (entity instanceof Player player)
                player.performCommand(newCommand);
        }

        if (onClick != null) onClick.execute(event);
    }

    public void callOnRender(EntryRenderEvent event) {
        if (event.getViewer() instanceof Player player) {
            if (renderIf != null && renderIf.test(player))
                event.setCancelled(true);
        }

        if (onRender != null) onRender.execute(event);
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public List<String> getTags() {
        return tags;
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    public void addCommand(String cmd) {
        this.commands.add(cmd);
    }

    public void removeCommand(String cmd) {
        this.commands.remove(cmd);
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setMetaData(NamespacedKey key, Object object) {
        metaData.put(key,object);
    }

    public Object getMetaData(NamespacedKey key) {
        return metaData.get(key);
    }
}

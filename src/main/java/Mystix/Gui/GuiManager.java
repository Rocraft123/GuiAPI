package Mystix.Gui;

import Mystix.Events.EntryClickEvent;
import Mystix.Events.GuiCloseEvent;
import Mystix.GuiAPI.GuiAPI;
import Mystix.Pack.Loader.GuiPackLoader;
import Mystix.Pack.Models.GuiPack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GuiManager implements Listener {

    private static final List<GuiPack> loadedPacks = new ArrayList<>();

    static final HashMap<Inventory, Gui> openGuis = new HashMap<>();

    static Gui getGui(Inventory inventory) {
        return openGuis.get(inventory);
    }

    static void addOpenGui(Inventory inv, Gui gui) {
        openGuis.put(inv, gui);
    }

    public static List<GuiPack> getLoadedPacks() {
        return loadedPacks;
    }

    public static GuiPack getPack(String name) {
        for (GuiPack pack : loadedPacks) {
            if (pack.getName().equalsIgnoreCase(name))
                return pack;
        }
        return null;
    }

    public static void addLoadedPack(GuiPack pack) {
        loadedPacks.add(pack);
    }

    public static void reloadPacks(Plugin plugin) {
        loadedPacks.clear();
        loadPacks(plugin);

        Bukkit.broadcast(Component.text("Reloaded GuiPacks.").color(NamedTextColor.GREEN));
    }

    public static Gui getCurrentGui(Player player) {
        return getGui(player.getOpenInventory().getTopInventory());
    }

    public static void loadPacks(Plugin plugin) {
        File packPath = new File(plugin.getDataFolder(), "GuiPacks");
        if (packPath.mkdirs()) {
            GuiAPI.logger.config("Created Packs folder.");
            return;
        }

        File[] packs = packPath.listFiles();
        if (packs == null || packs.length == 0) {
            GuiAPI.logger.info("Could not find an packs to load.");
            return;
        }

        int packsLoaded = 0;
        for (File file : packs) {
            GuiPackLoader loader = new GuiPackLoader(file, plugin);
            GuiPack pack = loader.loadPack();

            if (pack == null) {
                plugin.getLogger().warning("Failed to load pack: " + file);
                continue;
            }
            GuiAPI.logger.info("Successfully loaded guiPack: " + pack.getName());
            addLoadedPack(pack);
            packsLoaded++;
        }

        GuiAPI.logger.info("Successfully loaded " + packsLoaded + " guiPacks.");
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inv = event.getClickedInventory();
        if (inv == null) return;
        Gui gui = getGui(inv);

        if (gui == null) return;
        int slot = event.getSlot();
        Entry entry = gui.getEntry(slot);
        if (entry == null) return;

        EntryClickEvent clickEvent = new EntryClickEvent(gui, entry, event.getClick(), event.getAction(),
                event.getWhoClicked(), slot);

        gui.callOnClick(clickEvent);
        entry.callOnClick(clickEvent);

        Bukkit.getPluginManager().callEvent(clickEvent);
        event.setCancelled(clickEvent.isCancelled());
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        Gui gui = getGui(inventory);

        if (gui == null) return;

        GuiCloseEvent closeEvent = new GuiCloseEvent(gui, event.getPlayer());
        gui.callOnClose(closeEvent);
        Bukkit.getPluginManager().callEvent(closeEvent);

        openGuis.remove(inventory);
    }
}

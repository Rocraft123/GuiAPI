package Mystix.GuiAPI;

import Mystix.GuiAPI.Event.EventManager;
import Mystix.GuiAPI.Event.Events.EntryClickEvent;
import Mystix.GuiAPI.Event.Events.GuiClickEvent;
import Mystix.GuiAPI.Event.Events.GuiCloseEvent;
import Mystix.GuiAPI.Gui.Builders.GuiBuilder;
import Mystix.GuiAPI.Gui.Entry;
import Mystix.GuiAPI.Gui.Flags.EntryFlags;
import Mystix.GuiAPI.Gui.Flags.GuiFlags;
import Mystix.GuiAPI.Gui.Gui;
import Mystix.GuiAPI.Gui.GuiHolder;
import Mystix.GuiAPI.Utils.Cooldown.Cooldown;
import Mystix.GuiAPI.Utils.Cooldown.CooldownManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Central runtime controller for the GUI API.
 *
 * <p>
 * {@code InitializedGuiAPI} represents a fully configured and active instance
 * of the GUI system. It is responsible for:
 * </p>
 *
 * <ul>
 *   <li>Registering Bukkit event listeners</li>
 *   <li>Dispatching GUI-related events</li>
 *   <li>Handling inventory interactions</li>
 *   <li>Managing debug output</li>
 * </ul>
 *
 * <h2>Event system</h2>
 * <p>
 * This class intercepts Bukkit inventory events and translates them into
 * API-level events such as:
 * </p>
 *
 * <ul>
 *   <li>{@link GuiClickEvent}</li>
 *   <li>{@link EntryClickEvent}</li>
 *   <li>{@link GuiCloseEvent}</li>
 * </ul>
 *
 * These events are dispatched through {@link EventManager}.
 *
 * <h2>Example</h2>
 * <pre>{@code
 * InitializedGuiAPI api = GuiAPI.initialize()
 *     .provider(plugin)
 *     .debug(true)
 *     .build();
 *
 * Gui gui = api.createGui(Component.text("Menu"), 27)
 *     .build();
 *
 * gui.open(player);
 * }</pre>
 *
 * @see Gui
 * @see GuiBuilder
 * @see EventManager
 *
 * @since 0.1.0-alpha
 */
public final class InitializedGuiAPI implements Listener {

    private final Plugin provider;
    private final Logger logger;
    private final boolean debug;

    private final EventManager eventManager = new EventManager(this);
    private final Map<UUID, CooldownManager<Entry>> cooldownManager = new HashMap<>();

    /**
     * Creates and initializes the GUI API runtime.
     *
     * <p>
     * This constructor registers internal listeners automatically.
     * </p>
     *
     * @param provider the owning plugin
     * @param logger the logger to use
     * @param debug whether debug logging is enabled
     *
     * @throws NullPointerException if {@code provider} or {@code logger} is null
     */
    InitializedGuiAPI(Plugin provider, Logger logger, boolean debug) {
        this.provider = provider;
        this.logger = logger;
        this.debug = debug;

        provider.getServer().getPluginManager().registerEvents(this, provider);
    }

    /**
     * Logs a debug message if debug mode is enabled.
     *
     * @param level the log level
     * @param message the message to log
     */
    public void debug(Level level, String message) {
        if (debug) logger.log(level, message);
    }

    /**
     * @return the event manager
     */
    public EventManager getEventManager() {
        return eventManager;
    }

    /**
     * @return the cooldown manager
     */
    public CooldownManager<Entry> getCooldownManager(UUID uuid) {
        return cooldownManager.computeIfAbsent(uuid, u -> new CooldownManager<>());
    }

    /**
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * @return the owning plugin
     */
    public Plugin getProvider() {
        return provider;
    }

    /**
     * @return {@code true} if debug is enabled
     */
    public boolean isDebug() {
        return debug;
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (!(event.getInventory().getHolder() instanceof GuiHolder(Gui gui))) return;

        int slot = event.getRawSlot();
        if (slot < 0 || slot >= gui.getSize()) return;

        Entry entry = gui.getEntry(slot);
        if (entry == null) return;

        GuiClickEvent guiClickEvent = new GuiClickEvent(gui, entry, player, slot, event.getAction(), event.getClick());
        eventManager.callEvent(guiClickEvent);

        if (gui.hasFlag(GuiFlags.ON_CLICK))
            entry.getFlag(GuiFlags.ON_CLICK).accept(guiClickEvent);

        EntryClickEvent entryClickEvent = new EntryClickEvent(entry, gui, player, slot, event.getAction(), event.getClick());
        entryClickEvent.setCancelled(guiClickEvent.isCancelled());
        eventManager.callEvent(entryClickEvent);

        if (entryClickEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        CooldownManager<Entry> cooldownManager = getCooldownManager(player.getUniqueId());

        if (entry.hasFlag(EntryFlags.COOLDOWN)) {
            if (cooldownManager.isOnCooldown(entry)) {
                event.setCancelled(true);
                return;
            }
            cooldownManager.setCooldown(entry, new Cooldown(entry.getFlag(EntryFlags.COOLDOWN)));
        }

        if (entry.hasFlag(EntryFlags.PERMISSION)) {
            if (!player.hasPermission(entry.getFlag(EntryFlags.PERMISSION))) {
                event.setCancelled(true);

                if (entry.hasFlag(EntryFlags.PERMISSION_MSG))
                    player.sendMessage(entry.getFlag(EntryFlags.PERMISSION_MSG));
                return;
            }
        }
        if (entry.hasFlag(EntryFlags.COMMANDS)) {
            List<String> commands = entry.getFlag(EntryFlags.COMMANDS);
            for (String command : commands)
                player.chat("/" + command.replace("/", ""));
        }


        if (entry.hasFlag(EntryFlags.MESSAGE)) player.sendMessage(entry.getFlag(EntryFlags.MESSAGE));
        if (entry.hasFlag(EntryFlags.CLICK_SOUND)) player.playSound(entry.getFlag(EntryFlags.CLICK_SOUND));
        if (entry.hasFlag(EntryFlags.ON_CLICK)) entry.getFlag(EntryFlags.ON_CLICK).accept(entryClickEvent);

        if (entry.hasFlag(EntryFlags.CANCEL)) event.setCancelled(entry.getFlag(EntryFlags.CANCEL));
        else event.setCancelled(entryClickEvent.isCancelled());

        if (!event.isCancelled() && entry.hasFlag(EntryFlags.NAVIGATE_TO))
            entry.getFlag(EntryFlags.NAVIGATE_TO).open(player);
    }


    @EventHandler
    private void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        if (!(event.getInventory().getHolder() instanceof GuiHolder(Gui gui))) return;

        GuiCloseEvent closeEvent = new GuiCloseEvent(gui, player, event.getReason());
        eventManager.callEvent(closeEvent);

        if (gui.hasFlag(GuiFlags.ON_CLOSE))
            gui.getFlag(GuiFlags.ON_CLOSE).accept(closeEvent);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (closeEvent.isCancelled())
                    gui.open(player);
            }
        }.runTaskLater(provider, 1);
    }
}

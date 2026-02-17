package Mystix.GuiAPI.Gui;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Internal inventory holder for GUI-backed inventories.
 *
 * <p>
 * {@code GuiHolder} links a Bukkit {@link Inventory} to its owning {@link Gui}
 * instance. It is used internally to identify GUI inventories during
 * event handling.
 * </p>
 *
 * <p>
 * This class is not intended for direct use by API consumers.
 * </p>
 *
 * @param gui the owning GUI instance
 *
 * @since 0.1.0-alpha
 */
public record GuiHolder(Gui gui) implements InventoryHolder {
    /**
     * Returns a placeholder inventory.
     *
     * <p>
     * The returned inventory is not used for rendering and exists only
     * to satisfy the {@link InventoryHolder} contract.
     * </p>
     *
     * @return a dummy inventory instance
     */
    @Override @ApiStatus.Internal
    public @NotNull Inventory getInventory() {
        return Bukkit.createInventory(null, 54);
    }
}

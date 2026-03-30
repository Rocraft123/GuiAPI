package Mystix.GuiAPI.Gui.Guis;

import Mystix.GuiAPI.Gui.Gui;
import Mystix.GuiAPI.InitializedGuiAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

import java.util.UUID;

/**
 * EXPERIMENTAL – INTERNAL API
 *
 * <p>This class is under active development and is not safe for public use.
 * Behavior, signatures, and output may change without notice.</p>
 *
 * <p>Using this class in production code is unsupported.</p>
 */
@ApiStatus.Experimental
@ApiStatus.Internal
@Deprecated(forRemoval = false, since = "0.1.0-alpha-4")
public class TexturedGui extends Gui {

    /**
     * Creates a new TexturedGui.
     *
     * @param title the title displayed at the top of the inventory
     * @param size  the size of the GUI (must be a multiple of 9)
     * @param api   the initialized GUI API instance
     * @throws IllegalArgumentException if {@code size} is not a multiple of 9
     */
    public TexturedGui(Component title, int size, InitializedGuiAPI api) {
        super(title, size, api);
    }
}

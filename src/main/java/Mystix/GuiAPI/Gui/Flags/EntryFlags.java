package Mystix.GuiAPI.Gui.Flags;

import Mystix.GuiAPI.Event.Events.EntryClickEvent;
import Mystix.GuiAPI.Event.Events.EntryRenderEvent;
import Mystix.GuiAPI.Gui.Gui;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;

import java.util.List;
import java.util.function.Consumer;

/**
 * Collection of predefined flags that apply to {@link Mystix.GuiAPI.Gui.Entry} instances.
 *
 * <p>
 * Entry flags control click behavior, commands, permissions,
 * navigation, and rendering.
 * </p>
 *
 * @see GuiFlags
 * @see Flag
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public final class EntryFlags {

    private EntryFlags() {}

    /**
     * Called when an entry is clicked.
     */
    public static final Flag<Consumer<EntryClickEvent>> ON_CLICK = Flag.of("on_click_entry", (Class) Consumer.class);
    /**
     * Called when an entry is rendered.
     */
    public static final Flag<Consumer<EntryRenderEvent>> ON_RENDER = Flag.of("on_render_entry", (Class) Consumer.class);
    /**
     * Commands executed when clicked.
     */
    public static final Flag<List<String>> COMMANDS = Flag.of("command_entry", (Class) List.class);
    /**
     * Whether the click should be canceled.
     */
    public static final Flag<Boolean> CANCEL = Flag.of("cancel", Boolean.class);
    /**
     * Required permission to interact.
     */
    public static final Flag<String> PERMISSION = Flag.of("permission", String.class);
    /**
     * Message shown when permission is missing.
     */
    public static final Flag<Component> PERMISSION_MSG = Flag.of("permission_msg", Component.class);
    /**
     * Message sent on interaction.
     */
    public static final Flag<Component> MESSAGE = Flag.of("message", Component.class);
    /**
     * Click cooldown in ticks.
     */
    public static final Flag<Integer> COOLDOWN = Flag.of("cooldown", Integer.class);
    /**
     * Sound played on click.
     */
    public static final Flag<Sound> CLICK_SOUND = Flag.of("click_sound", Sound.class);
    /**
     * Target GUI to open on click.
     */
    public static final Flag<Gui> NAVIGATE_TO = Flag.of("navigate_to", Gui.class);
}

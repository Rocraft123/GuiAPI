package Mystix.GuiAPI.Gui.Flags;

import Mystix.GuiAPI.Event.Events.GuiClickEvent;
import Mystix.GuiAPI.Event.Events.GuiCloseEvent;
import Mystix.GuiAPI.Event.Events.GuiOpenEvent;
import Mystix.GuiAPI.Event.Events.GuiRenderEvent;

import java.util.function.Consumer;

/**
 * Collection of predefined flags that apply to {@link Mystix.GuiAPI.Gui.Gui} instances.
 *
 * <p>
 * These flags control GUI-level behavior such as opening, rendering,
 * clicking, and closing.
 * </p>
 *
 * <p>
 * Flags can be attached to a GUI using the flag system.
 * </p>
 *
 * @see EntryFlags
 * @see Flag
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public final class GuiFlags {

    private GuiFlags() {}

    /**
     * Called when a GUI is clicked.
     */
    public static final Flag<Consumer<GuiClickEvent>> ON_CLICK = Flag.of("on_click_gui", (Class) Consumer.class);
    /**
     * Called when a GUI is rendered.
     */
    public static final Flag<Consumer<GuiRenderEvent>> ON_RENDER = Flag.of("on_render_gui", (Class) Consumer.class);
    /**
     * Called when a GUI is opened.
     */
    public static final Flag<Consumer<GuiOpenEvent>> ON_OPEN = Flag.of("on_open", (Class) Consumer.class);
    /**
     * Called when a GUI is closed.
     *
     */
    public static final Flag<Consumer<GuiCloseEvent>> ON_CLOSE = Flag.of("on_close", (Class) Consumer.class);
}


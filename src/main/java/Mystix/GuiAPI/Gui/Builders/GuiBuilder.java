package Mystix.GuiAPI.Gui.Builders;

import Mystix.GuiAPI.Event.Events.GuiClickEvent;
import Mystix.GuiAPI.Event.Events.GuiCloseEvent;
import Mystix.GuiAPI.Event.Events.GuiOpenEvent;
import Mystix.GuiAPI.Event.Events.GuiRenderEvent;
import Mystix.GuiAPI.Gui.Entry;
import Mystix.GuiAPI.Gui.Flags.GuiFlags;
import Mystix.GuiAPI.Gui.Gui;
import Mystix.GuiAPI.InitializedGuiAPI;
import Mystix.GuiAPI.Utils.Pattern;
import net.kyori.adventure.text.Component;

import java.util.Map;
import java.util.function.Consumer;


/**
 * Builder used to construct and configure {@link Gui} instances.
 * <p>
 * Provides fluent methods for modifying entries, flags,
 * layout utilities, and event handlers.
 */
public class GuiBuilder implements Builder<Gui> {

    protected final Gui gui;

    /**
     * Creates a new GUI builder.
     *
     * @param title GUI title
     * @param size  inventory size
     * @param api   initialized API instance
     */
    public GuiBuilder(Component title, int size, InitializedGuiAPI api) {
        gui = new Gui(title, size, api);
    }

    /**
     * Sets an entry at a specific slot.
     *
     * @param slot  slot index
     * @param entry entry to place
     * @return this builder
     */
    public GuiBuilder setEntry(int slot, Entry entry) {
        gui.setEntry(slot, entry);
        return this;
    }

    /**
     * Adds an entry to the next available slot.
     *
     * @param entry entry to add
     * @return this builder
     */
    public GuiBuilder addEntry(Entry entry) {
        gui.addEntry(entry);
        return this;
    }

    /**
     * Removes the entry at the given slot.
     *
     * @param slot slot index
     * @return this builder
     */
    public GuiBuilder remove(int slot) {
        gui.remove(slot);
        return this;
    }

    /**
     * Removes a specific entry instance.
     *
     * @param entry entry to remove
     * @return this builder
     */
    public GuiBuilder remove(Entry entry) {
        gui.remove(entry);
        return this;
    }

    /**
     * Updates the GUI title.
     *
     * @param title new title
     * @return this builder
     */
    public GuiBuilder setTitle(Component title) {
        gui.setTitle(title);
        return this;
    }

    /**
     * Sets the global click handler.
     *
     * @param eventConsumer click consumer
     * @return this builder
     */
    public GuiBuilder setOnClick(Consumer<GuiClickEvent> eventConsumer) {
        gui.setFlag(GuiFlags.ON_CLICK, eventConsumer);
        return this;
    }

    /**
     * Sets the render handler.
     *
     * @param eventConsumer render consumer
     * @return this builder
     */
    public GuiBuilder setOnRender(Consumer<GuiRenderEvent> eventConsumer) {
        gui.setFlag(GuiFlags.ON_RENDER, eventConsumer);
        return this;
    }

    /**
     * Sets the open handler.
     *
     * @param eventConsumer open consumer
     * @return this builder
     */
    public GuiBuilder setOnOpen(Consumer<GuiOpenEvent> eventConsumer) {
        gui.setFlag(GuiFlags.ON_OPEN, eventConsumer);
        return this;
    }

    /**
     * Sets the close handler.
     *
     * @param eventConsumer close consumer
     * @return this builder
     */
    public GuiBuilder setOnClose(Consumer<GuiCloseEvent> eventConsumer) {
        gui.setFlag(GuiFlags.ON_CLOSE, eventConsumer);
        return this;
    }

    /**
     * Fills the entire GUI with the given entry.
     *
     * @param entry entry template
     * @return this builder
     */
    public GuiBuilder fill(Entry entry) {
        for (int i = 0; i < gui.getSize(); i++)
            gui.setEntry(i, entry.clone());

        return this;
    }

    /**
     * Fills the given slots with the given entry.
     *
     * @param entry entry template
     * @param slots the slots that will be filled
     * @return this builder
     */
    public GuiBuilder fill(Entry entry, int... slots) {
        for (int i : slots)
            gui.setEntry(i, entry);
        return this;
    }

    /**
     * Fills the selected row
     *
     * @param row row number
     * @param entry entry template
     * @return this builder
     */
    public GuiBuilder fillRow(int row, Entry entry) {
        int start = (row - 1) * 9;
        int end = start + 9;

        for (int i = start; i < end; i++)
            gui.setEntry(i, entry.clone());
        return this;
    }

    /**
     * Fills the selected column
     *
     * @param column column number
     * @param entry entry template
     * @return this builder
     */
    public GuiBuilder fillColumn(int column, Entry entry) {
        if (column < 1 || column > 9)
            throw new IllegalArgumentException("Column must be between 1 and 9");

        for (int i = column - 1; i < gui.getSize(); i += 9)
            gui.setEntry(i, entry.clone());
        return this;
    }

    /**
     * Fills all the borders
     *
     * @param entry entry template
     * @return this builder
     */
    public GuiBuilder fillBorders(Entry entry) {
        int size = gui.getSize();
        int rows = size / 9;

        for (int i = 0; i < size; i++) {
            int row = i / 9;
            int col = i % 9;

            if (row == 0 || row == rows - 1 || col == 0 || col == 8)
                gui.setEntry(i, entry.clone());

        }
        return this;
    }

    /**
     * Fills all the lots in between the selected slots.
     *
     * @param startSlot the starting slot
     * @param endSlot the ending slot
     * @param entry entry template
     * @return this builder
     */
    public GuiBuilder fillRange(int startSlot, int endSlot, Entry entry) {
        for (int i = startSlot; i <= endSlot && i < gui.getSize(); i++)
            gui.setEntry(i, entry.clone());
        return this;
    }

    /**
     * Applies a layout pattern to the GUI.
     *
     * @param pattern pattern definition
     * @return this builder
     */
    public GuiBuilder pattern(Pattern pattern) {
        String[] layout = pattern.getLayout();
        Map<Character, Entry> bindings = pattern.getBindings();

        for (int row = 0; row < layout.length; row++) {
            String line = layout[row];
            if (line == null) continue;

            for (int col = 0; col < line.length(); col++) {
                char symbol = line.charAt(col);
                Entry entry = bindings.get(symbol);
                if (entry != null) {
                    int slot = row * 9 + col;
                    gui.setEntry(slot, entry);
                }
            }
        }
        return this;
    }

    /**
     * Applies additional builder logic.
     *
     * @param builderConsumer consumer receiving this builder
     * @return this builder
     */
    public GuiBuilder function(Consumer<GuiBuilder> builderConsumer) {
        builderConsumer.accept(this);
        return this;
    }

    /**
     * Builds the GUI instance.
     *
     * @return built GUI
     */
    @Override
    public Gui build() {
        gui.attachBuilder(this);
        return gui;
    }
}

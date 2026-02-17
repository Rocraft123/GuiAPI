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

public class GuiBuilder implements Builder<Gui> {

    protected final Gui gui;

    public GuiBuilder(Component title, int size, InitializedGuiAPI api) {
        gui = new Gui(title, size, api);
    }

    public GuiBuilder setEntry(int slot, Entry entry) {
        gui.setEntry(slot, entry);
        return this;
    }

    public GuiBuilder addEntry(Entry entry) {
        gui.addEntry(entry);
        return this;
    }

    public GuiBuilder remove(int slot) {
        gui.remove(slot);
        return this;
    }

    public GuiBuilder remove(Entry entry) {
        gui.remove(entry);
        return this;
    }

    public GuiBuilder setTitle(Component title) {
        gui.setTitle(title);
        return this;
    }

    public GuiBuilder setOnClick(Consumer<GuiClickEvent> eventConsumer) {
        gui.setFlag(GuiFlags.ON_CLICK, eventConsumer);
        return this;
    }

    public GuiBuilder setOnRender(Consumer<GuiRenderEvent> eventConsumer) {
        gui.setFlag(GuiFlags.ON_RENDER, eventConsumer);
        return this;
    }

    public GuiBuilder setOnOpen(Consumer<GuiOpenEvent> eventConsumer) {
        gui.setFlag(GuiFlags.ON_OPEN, eventConsumer);
        return this;
    }

    public GuiBuilder setOnClose(Consumer<GuiCloseEvent> eventConsumer) {
        gui.setFlag(GuiFlags.ON_CLOSE, eventConsumer);
        return this;
    }

    public GuiBuilder fill(Entry entry) {
        for (int i = 0; i < gui.getSize(); i++)
            gui.setEntry(i, entry.clone());

        return this;
    }

    public GuiBuilder fill(Entry entry, int... slots) {
        for (int i : slots)
            gui.setEntry(i, entry);
        return this;
    }

    public GuiBuilder fillRow(int row, Entry entry) {
        int start = (row - 1) * 9;
        int end = start + 9;

        for (int i = start; i < end; i++)
            gui.setEntry(i, entry.clone());
        return this;
    }

    public GuiBuilder fillColumn(int column, Entry entry) {
        if (column < 1 || column > 9)
            throw new IllegalArgumentException("Column must be between 1 and 9");

        for (int i = column - 1; i < gui.getSize(); i += 9)
            gui.setEntry(i, entry.clone());
        return this;
    }

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

    public GuiBuilder fillRange(int startSlot, int endSlot, Entry entry) {
        for (int i = startSlot; i <= endSlot && i < gui.getSize(); i++)
            gui.setEntry(i, entry.clone());
        return this;
    }

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

    public GuiBuilder function(Consumer<GuiBuilder> builderConsumer) {
        builderConsumer.accept(this);
        return this;
    }

    @Override
    public Gui build() {
        gui.attachBuilder(this);
        return gui;
    }
}

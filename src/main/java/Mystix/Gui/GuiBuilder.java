package Mystix.Gui;

import Mystix.Functions.EntryClickFunction;
import Mystix.Functions.GuiCloseFunction;
import Mystix.Functions.GuiOpenFunction;
import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryType;

import java.util.List;
import java.util.function.IntFunction;

/**
 * A fluent builder for constructing {@link Gui} objects.
 */
public class GuiBuilder {

    private final Gui gui;

    /**
     * Creates a builder with a chest-style GUI.
     *
     * @param title GUI title.
     * @param size  Inventory size (must be multiple of 9).
     */
    public GuiBuilder(Component title, int size) {
        this.gui = new Gui(title, size);
    }
    /**
     * Creates a builder for a custom inventory type.
     *
     * @param title GUI title.
     * @param type  InventoryType.
     */
    public GuiBuilder(Component title, InventoryType type) {
        this.gui = new Gui(title, type);
    }

    public GuiBuilder setEntries(List<Integer> slots, Entry entry) {
        for (int i : slots)
            setEntry(i, entry);
        return this;
    }

    public GuiBuilder fillEntries(int start, int end, IntFunction<Entry> entryFunction) {
        for (int i = start; i <= end; i++) {
            setEntry(i, entryFunction.apply(i));
        }
        return this;
    }

    public GuiBuilder fillEntries(int start, int end, Entry entry) {
        for (int i = start; i < end + 1; i++)
            setEntry(i, entry);

        return this;
    }

    public GuiBuilder setEntry(int slot, Entry entry) {
        gui.setEntry(slot, entry);
        return this;
    }

    public GuiBuilder onOpen(GuiOpenFunction function) {
        gui.setOnOpen(function);
        return this;
    }

    public GuiBuilder onClose(GuiCloseFunction function) {
        gui.setOnClose(function);
        return this;
    }

    public GuiBuilder onClick(EntryClickFunction function) {
        gui.setOnClick(function);
        return this;
    }

    public GuiBuilder fillBorder(Entry entry) {
        int rows = gui.getSize() / 9;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < 9; col++) {
                int slot = row * 9 + col;

                if (row == 0 || row == rows - 1 ||
                        col == 0 || col == 8) {
                    setEntry(slot, entry);
                }
            }
        }
        return this;
    }

    public GuiBuilder fillFrame(Entry entry) {
        int rows = gui.getSize() / 9;

        for (int row = 1; row < rows - 1; row++) {
            for (int col = 1; col < 8; col++) {
                int slot = row * 9 + col;
                setEntry(slot, entry);
            }
        }
        return this;
    }

    public Gui build() {
        return gui;
    }
}

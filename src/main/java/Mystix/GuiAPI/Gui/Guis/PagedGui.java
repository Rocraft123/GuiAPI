package Mystix.GuiAPI.Gui.Guis;

import Mystix.GuiAPI.Gui.Builders.GuiBuilder;
import Mystix.GuiAPI.Gui.Entry;
import Mystix.GuiAPI.Gui.Gui;
import Mystix.GuiAPI.InitializedGuiAPI;
import Mystix.GuiAPI.Utils.SlotArea;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

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
@Deprecated(forRemoval = false, since = "0.1.0-alpha-4")class PagedGui<T> extends Gui {

    private final List<T> items;
    private final Function<T, Entry> supplier;

    private SlotArea area;

    public PagedGui(Component title, int size, InitializedGuiAPI api,
                    List<T> items, Function<T, Entry> supplier) {
        super(title, size, api);

        this.items = items;
        this.supplier = supplier;
        this.area = SlotArea.full(size);
    }

    public void setArea(SlotArea area) {
        this.area = area;
    }

    public void addItem(T item) {
        items.add(item);
    }

    public void removeItem(T item) {
        items.remove(item);
    }

    public List<T> getItems() {
        return Collections.unmodifiableList(items);
    }

    public Gui buildPage() {
        return null;
    }
}

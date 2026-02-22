package Mystix.GuiAPI.Gui.Builders;

import Mystix.GuiAPI.Gui.Entry;
import Mystix.GuiAPI.Gui.Gui;
import Mystix.GuiAPI.InitializedGuiAPI;
import Mystix.GuiAPI.Utils.SlotArea;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Extension of {@link GuiBuilder} that provides built-in pagination support.
 *
 * <p>This builder maps a list of source objects into {@link Entry} instances
 * and distributes them across GUI pages.</p>
 *
 * <p>Pages are zero-indexed.</p>
 *
 * @param <T> source item type used to generate entries
 */
public class PagedGuiBuilder<T> extends GuiBuilder {

    protected final List<T> items;
    protected final Function<T, Entry> entryMapper;
    protected SlotArea area;
    protected final int pageSize;
    protected int currentPage;
    protected Consumer<Integer> onPageChange = page -> {};
    protected BiConsumer<PagedGuiBuilder<T>, Integer> onRenderPage = (g, page) -> {};

    /**
     * Creates a new paged GUI builder.
     *
     * @param title        GUI title
     * @param size         GUI size (must be multiple of 9)
     * @param api          initialized API instance
     * @param items        source items to paginate
     * @param entryMapper  mapper converting items into {@link Entry}
     * @param pageSize     maximum items per page
     */
    public PagedGuiBuilder(Component title, int size, InitializedGuiAPI api,
                           List<T> items, Function<T, Entry> entryMapper, int pageSize) {
        super(title, size, api);
        this.items = items;
        this.entryMapper = entryMapper;
        this.pageSize = pageSize;
    }

    /**
     * Sets a callback invoked when the page changes.
     *
     * @param consumer page change consumer
     * @return this builder
     */
    public PagedGuiBuilder<T> onPageChange(Consumer<Integer> consumer) {
        this.onPageChange = consumer;
        return this;
    }

    /**
     * Sets a callback invoked after a page is rendered.
     *
     * @param consumer render consumer
     * @return this builder
     */
    public PagedGuiBuilder<T> onRenderPage(BiConsumer<PagedGuiBuilder<T>, Integer> consumer) {
        this.onRenderPage = consumer;
        return this;
    }

    /**
     * Builds and renders the specified page.
     *
     * <p>This will:</p>
     * <ul>
     *     <li>Clear the pagination area</li>
     *     <li>Map items to entries</li>
     *     <li>Place entries into slots</li>
     *     <li>Invoke render and page callbacks</li>
     * </ul>
     *
     * @param page zero-based page index
     * @return the built GUI
     */
    public Gui buildPage(int page) {
        this.currentPage = page;

        List<Integer> slots = area == null
                ? SlotArea.full(getGui().getSize()).slots()
                : area.slots();

        for (int slot : slots)
            remove(slot);

        int start = page * pageSize;
        int end = Math.min(start + pageSize, items.size());

        int used = 0;

        for (int i = start; i < end; i++) {
            int slotIndex = i - start;
            if (slotIndex >= slots.size()) break;

            setEntry(slots.get(slotIndex),
                    entryMapper.apply(items.get(i)));
            used++;
        }

        for (int i = used; i < slots.size(); i++)
            remove(slots.get(i));

        onRenderPage.accept(this, page);
        onPageChange.accept(page);

        gui.attachBuilder(this);
        return gui;
    }

    /**
     * Advances to the next page if available.
     */
    public void nextPage() {
        if (currentPage + 1 < getTotalPages())
            buildPage(currentPage + 1);
    }

    /**
     * Goes back to the previous page if available.
     */
    public void prevPage() {
        if (currentPage > 0)
            buildPage(currentPage - 1);
    }

    /**
     * Gets the currently active page.
     *
     * @return zero-based page index
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Calculates the total number of pages.
     *
     * @return total page count
     */
    public int getTotalPages() {
        return (int) Math.ceil(items.size() / (double) pageSize);
    }

    /**
     * Sets the slot area used for pagination.
     *
     * <p>If not set, the full GUI area is used.</p>
     *
     * @param area slot area
     */
    public void setArea(SlotArea area) {
        this.area = area;
    }

    /**
     * Gets the current pagination area.
     *
     * @return slot area or null if using full GUI
     */
    public SlotArea getArea() {
        return area;
    }

    /**
     * Internal access to the backing GUI.
     *
     * @return backing GUI
     */
    protected Gui getGui() {
        gui.attachBuilder(this);
        return gui;
    }
}
package Mystix.GuiAPI.Gui.Builders;

import Mystix.GuiAPI.Gui.Entry;
import Mystix.GuiAPI.Gui.Gui;
import Mystix.GuiAPI.InitializedGuiAPI;
import Mystix.GuiAPI.Utils.SlotArea;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class PagedGuiBuilder<T> extends GuiBuilder {

    protected final List<T> items;
    protected final Function<T, Entry> entryMapper;
    protected SlotArea area;
    protected final int pageSize;
    protected int currentPage;

    protected Consumer<Integer> onPageChange = page -> {};
    protected BiConsumer<PagedGuiBuilder<T>, Integer> onRenderPage = (g, page) -> {};

    public PagedGuiBuilder(Component title, int size, InitializedGuiAPI api,
                           List<T> items, Function<T, Entry> entryMapper, int pageSize) {
        super(title, size, api);
        this.items = items;
        this.entryMapper = entryMapper;
        this.pageSize = pageSize;
    }

    public PagedGuiBuilder<T> onPageChange(Consumer<Integer> consumer) {
        this.onPageChange = consumer;
        return this;
    }

    public PagedGuiBuilder<T> onRenderPage(BiConsumer<PagedGuiBuilder<T>, Integer> consumer) {
        this.onRenderPage = consumer;
        return this;
    }

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

    public void nextPage() {
        if (currentPage + 1 < getTotalPages())
            buildPage(currentPage + 1);
    }

    public void prevPage() {
        if (currentPage > 0)
            buildPage(currentPage - 1);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return (int) Math.ceil(items.size() / (double) pageSize);
    }

    public void setArea(SlotArea area) {
        this.area = area;
    }

    public SlotArea getArea() {
        return area;
    }

    protected Gui getGui() {
        gui.attachBuilder(this);
        return gui;
    }
}



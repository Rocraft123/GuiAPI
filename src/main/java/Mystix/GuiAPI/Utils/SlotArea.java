package Mystix.GuiAPI.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a reusable collection of GUI slot indices.
 *
 * <p>
 * {@code SlotArea} is primarily used by paged and composite GUI builders
 * to define regions that may be populated dynamically.
 * </p>
 *
 *
 * @param slots the slot indices
 *
 * @see Mystix.GuiAPI.Gui.Builders.PagedGuiBuilder
 * @see Pattern
 *
 * @since 0.1.0-alpha
 */
public record SlotArea(List<Integer> slots) {

    /**
     * Creates a slot area from a continuous range.
     *
     * @param start first slot (inclusive)
     * @param end last slot (inclusive)
     * @return a slot area
     */
    public static SlotArea ofRange(int start, int end) {
        List<Integer> slots = new ArrayList<>();
        for (int i = start; i <= end; i++) slots.add(i);
        return new SlotArea(slots);
    }

    /**
     * Creates a slot area from full rows.
     *
     * @param rows the row indices
     * @return a slot area
     */
    public static SlotArea ofRows(int... rows) {
        List<Integer> slots = new ArrayList<>();
        for (int row : rows)
            for (int i = 0; i < 9; i++)
                slots.add(row * 9 + i);
        return new SlotArea(slots);
    }

    /**
     * Creates a slot area covering the entire GUI.
     *
     * @param guiSize the GUI size
     * @return a full slot area
     */
    public static SlotArea full(int guiSize) {
        List<Integer> slots = new ArrayList<>();
        for (int i = 0; i < guiSize; i++)
            slots.add(i);

        return new SlotArea(slots);
    }
}

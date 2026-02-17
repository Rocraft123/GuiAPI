package Mystix.GuiAPI.Utils;

import Mystix.GuiAPI.Gui.Entry;

import java.util.*;

/**
 * Represents a symbolic layout pattern for populating GUI slots.
 *
 * <p>
 * A {@code Pattern} defines a grid of characters, where each character
 * represents a bound {@link Entry}. This allows GUIs to be constructed
 * using visual templates.
 * </p>
 *
 * <h2>Layout rules</h2>
 * <ul>
 *   <li>Each row must contain exactly 9 characters</li>
 *   <li>Total size must be a multiple of 9</li>
 *   <li>Unbound characters are ignored</li>
 * </ul>
 *
 * <h2>Bindings</h2>
 * <p>
 * Characters may be bound to {@link Entry} instances using
 * {@link #bind(char, Entry)}.
 * </p>
 *
 * <h2>Example</h2>
 * <pre>{@code
 * Pattern pattern = new Pattern(27)
 *     .setPattern(
 *         "axxxaxxxa",
 *         "xaxaxaxax",
 *         "xxaxxxaxx"
 *     )
 *     .bind('x', entry1)
 *     .bind('a', entry2);
 * }</pre>
 *
 * @see Patterns
 * @see Mystix.GuiAPI.Gui.Builders.GuiBuilder
 *
 * @since 0.1.0-alpha
 */
public class Pattern {
    private final int size;
    private final int rows;
    private final String[] layout;
    private final Map<Character, Entry> bindings = new HashMap<>();

    /**
     * Creates an empty pattern for the given GUI size.
     *
     * @param size the GUI size (multiple of 9)
     *
     * @throws IllegalArgumentException if size is invalid
     */
    public Pattern(int size) {
        if (size % 9 != 0)
            throw new IllegalArgumentException("GUI size must be a multiple of 9");
        this.size = size;
        this.rows = size / 9;
        this.layout = new String[this.rows];
    }

    /**
     * Creates a pattern from a predefined layout.
     *
     * @param layout the pattern rows
     *
     * @throws IllegalArgumentException if layout is invalid
     */
    public Pattern(String[] layout) {
        if (layout == null)
            throw new IllegalArgumentException("layout cannot be null");
        this.layout = layout.clone();
        this.rows = layout.length;
        this.size = this.rows * 9;

        for (String row : this.layout) {
            if (row == null || row.length() != 9)
                throw new IllegalArgumentException("Each pattern row must be exactly 9 characters");
        }
    }

    /**
     * Sets a single row pattern.
     *
     * @param row the row index
     * @param pattern the row layout (9 characters)
     */
    public Pattern setRow(int row, String pattern) {
        if (row < 0 || row >= this.rows) throw new IllegalArgumentException("Row out of bounds: " + row);
        if (pattern == null || pattern.length() != 9) throw new IllegalArgumentException("Pattern must be exactly 9 characters");
        this.layout[row] = pattern;
        return this;
    }

    /**
     * Sets the entire pattern layout.
     *
     * @param patterns the row layouts
     */
    public Pattern setPattern(String... patterns) {
        if (patterns.length != this.rows)
            throw new IllegalArgumentException("Expected " + this.rows + " rows, got " + patterns.length);
        for (int i = 0; i < this.rows; i++) setRow(i, patterns[i]);
        return this;
    }

    /**
     * Binds a character symbol to an entry.
     *
     * @param symbol the pattern character
     * @param entry the entry to bind
     */
    public Pattern bind(char symbol, Entry entry) {
        this.bindings.put(symbol, entry);
        return this;
    }

    /**
     *
     * @return a cloned layout array
     */
    public String[] getLayout() {
        return this.layout.clone();
    }


    /**
     * @return an unmodifiable bindings map
     */
    public Map<Character, Entry> getBindings() {
        return Collections.unmodifiableMap(this.bindings);
    }

    /**
     *
     * @return the GUI size
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Returns the number of rows.
     *
     * @return row count
     */
    public int getRows() {
        return this.rows;
    }

    /**
     * Finds all slots mapped to a symbol.
     *
     * @param symbol the pattern character
     * @return matching slot indices
     */
    public List<Integer> getSlotsFor(char symbol) {
        List<Integer> slots = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            String row = layout[r];
            if (row == null) continue;
            for (int c = 0; c < 9; c++) {
                if (row.charAt(c) == symbol) slots.add(r * 9 + c);
            }
        }
        return slots;
    }

    /**
     * Finds the first slot mapped to a symbol.
     *
     * @param symbol the pattern character
     * @return slot index, or {@code -1}
     */
    public int getFirstSlotFor(char symbol) {
        List<Integer> slots = getSlotsFor(symbol);
        return slots.isEmpty() ? -1 : slots.getFirst();
    }
}


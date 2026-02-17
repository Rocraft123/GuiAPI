package Mystix.GuiAPI.Utils;

import Mystix.GuiAPI.Gui.Entry;

/**
 * Utility factory for common GUI layout patterns.
 *
 * <p>
 * {@code Patterns} provides predefined {@link Pattern} generators for
 * frequently used layouts.
 * </p>
 *
 * <h2>Provided patterns</h2>
 * <ul>
 *   <li>Border</li>
 *   <li>Cross</li>
 *   <li>Chessboard</li>
 *   <li>Diagonal</li>
 *   <li>X-shape</li>
 * </ul>
 *
 * @see Pattern
 *
 * @since 0.1.0-alpha
 */
public final class Patterns {

    private Patterns() {}

    /**
     * Creates a border pattern.
     *
     * @param entry the border entry
     * @param rows number of rows
     * @return a border pattern
     */
    public static Pattern border(Entry entry, int rows) {
        String[] layout = new String[rows];
        for (int r = 0; r < rows; r++) {
            char[] row = new char[9];
            for (int c = 0; c < 9; c++) {
                if (r == 0 || r == rows - 1 || c == 0 || c == 8)
                    row[c] = 'x';
                else
                    row[c] = ' ';
            }
            layout[r] = new String(row);
        }
        return new Pattern(layout).bind('x', entry);
    }

    /**
     * Creates a cross pattern.
     */
    public static Pattern cross(Entry entry, int rows) {
        String[] layout = new String[rows];
        int centerRow = rows / 2;
        int centerCol = 4;
        for (int r = 0; r < rows; r++) {
            char[] row = new char[9];
            for (int c = 0; c < 9; c++) {
                if (r == centerRow || c == centerCol)
                    row[c] = 'x';
                else
                    row[c] = ' ';
            }
            layout[r] = new String(row);
        }
        return new Pattern(layout).bind('x', entry);
    }

    /**
     * Creates a chessboard pattern.
     */
    public static Pattern chessboard(Entry a, Entry b, int rows) {
        String[] layout = new String[rows];
        for (int r = 0; r < rows; r++) {
            char[] row = new char[9];
            for (int c = 0; c < 9; c++)
                row[c] = ((r + c) % 2 == 0) ? 'a' : 'b';

            layout[r] = new String(row);
        }
        return new Pattern(layout).bind('a', a).bind('b', b);
    }

    /**
     * Creates a diagonal pattern.
     */
    public static Pattern diagonal(Entry entry, int rows) {
        String[] layout = new String[rows];
        for (int r = 0; r < rows; r++) {
            char[] row = new char[9];
            for (int c = 0; c < 9; c++)
                row[c] = (r == c) ? 'x' : ' ';

            layout[r] = new String(row);
        }
        return new Pattern(layout).bind('x', entry);
    }

    /**
     * Creates an anti-diagonal pattern.
     */
    public static Pattern antiDiagonal(Entry entry, int rows) {
        String[] layout = new String[rows];
        for (int r = 0; r < rows; r++) {
            char[] row = new char[9];
            for (int c = 0; c < 9; c++)
                row[c] = (c == 8 - r) ? 'x' : ' ';

            layout[r] = new String(row);
        }
        return new Pattern(layout).bind('x', entry);
    }

    /**
     * Creates a corner pattern.
     */
    public static Pattern corners(Entry entry, int rows) {
        String[] layout = new String[rows];
        for (int r = 0; r < rows; r++) {
            char[] row = new char[9];
            for (int c = 0; c < 9; c++) {
                if ((r == 0 && c == 0) || (r == 0 && c == 8) ||
                        (r == rows - 1 && c == 0) || (r == rows - 1 && c == 8))
                    row[c] = 'x';
                else
                    row[c] = ' ';
            }
            layout[r] = new String(row);
        }
        return new Pattern(layout).bind('x', entry);
    }

    /**
     * Creates an X-shape pattern.
     */
    public static Pattern xShape(Entry entry, int rows) {
        String[] layout = new String[rows];
        for (int r = 0; r < rows; r++) {
            char[] row = new char[9];
            for (int c = 0; c < 9; c++) {
                if (r == c || c == 8 - r)
                    row[c] = 'x';
                else
                    row[c] = ' ';
            }
            layout[r] = new String(row);
        }
        return new Pattern(layout).bind('x', entry);
    }
}

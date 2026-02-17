package Mystix.GuiAPI.Gui.Flags;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.function.Supplier;

/**
 * Represents a typed configuration flag.
 *
 * <p>
 * A {@code Flag} acts as a key-value pair where the value
 * is strongly typed.
 * </p>
 *
 * <p>
 * Flags are used by {@link Mystix.GuiAPI.Gui.Gui} and {@link Mystix.GuiAPI.Gui.Entry} to store
 * additional metadata and behavior.
 * </p>
 *
 * @param <T> the value type of this flag
 *
 * @see GuiFlags
 * @see EntryFlags
 */
public final class Flag<T> implements Comparable<File> {

    private final String key;
    private final Class<T> type;
    private final Supplier<T> defaultSupplier;

    /**
     * Creates a new flag.
     *
     * @param key the unique flag key
     * @param type the expected value type
     * @param defaultSupplier supplies the default value
     */
    private Flag(String key, Class<T> type, Supplier<T> defaultSupplier) {
        this.key = key;
        this.type = type;
        this.defaultSupplier = defaultSupplier;
    }

    /**
     * Creates a flag with a {@code null} default value.
     *
     * @param key the flag key
     * @param type the value type
     * @param <T> the value type
     *
     * @return a new flag
     */
    public static <T> Flag<T> of(String key, Class<T> type) {
        return new Flag<>(key, type, () -> null);
    }

    /**
     * Creates a flag with a fixed default value.
     *
     * @param key the flag key
     * @param type the value type
     * @param defaultValue the default value
     * @param <T> the value type
     *
     * @return a new flag
     */
    public static <T> Flag<T> of(String key, Class<T> type, T defaultValue) {
        return new Flag<>(key, type, () -> defaultValue);
    }

    /**
     * Creates a flag with a dynamic default supplier.
     *
     * @param key the flag key
     * @param type the value type
     * @param defaultSupplier supplies the default value
     * @param <T> the value type
     *
     * @return a new flag
     */
    public static <T> Flag<T> of(String key, Class<T> type, Supplier<T> defaultSupplier) {
        return new Flag<>(key, type, defaultSupplier);
    }

    /**
     * @return the unique key
     */
    public String getKey() {
        return key;
    }

    /**
     *
     * @return the value class
     */
    public Class<T> getType() {
        return type;
    }

    /**
     *
     * @return default value (maybe {@code null})
     */
    public T getDefaultValue() {
        return defaultSupplier.get();
    }

    @Override
    public String toString() {
        return "Flag{" +
                "key='" + key + '\'' +
                ", type=" + type +
                ", defaultSupplier=" + defaultSupplier +
                '}';
    }

    /**
     * Compares flags by key.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flag<?> flag)) return false;
        return key.equals(flag.key);
    }

    /**
     * Compares this flag to a file.
     *
     * <p>
     * Currently unused.
     * </p>
     */
    @Override
    public int compareTo(@NotNull File o) {
        return 0;
    }
}

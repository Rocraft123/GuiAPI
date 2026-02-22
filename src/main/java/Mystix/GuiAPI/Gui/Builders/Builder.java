package Mystix.GuiAPI.Gui.Builders;

/**
 * Generic builder contract.
 *
 * @param <T> the type produced by the builder
 */
@FunctionalInterface
public interface Builder<T> {
    /**
     * Builds and returns the configured instance.
     *
     * @return built object
     */
    T build();
}

package Mystix.GuiAPI;

import org.bukkit.plugin.Plugin;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * Entry point and bootstrap utility for the GUI API.
 *
 * <p>
 * This class provides a fluent builder for initializing
 * {@link InitializedGuiAPI} instances.
 * </p>
 *
 * <h2>Initialization</h2>
 * <p>
 * The API must be initialized before any GUIs can be created.
 * </p>
 *
 * <h2>Example</h2>
 * <pre>{@code
 * InitializedGuiAPI api = GuiAPI.initialize()
 *     .provider(plugin)
 *     .logger(plugin.getLogger())
 *     .debug(true)
 *     .build();
 * }</pre>
 *
 * @see InitializedGuiAPI
 *
 * @since 0.1.0-alpha
 */
public class GuiAPI  {

    private GuiAPI() {}

    /**
     * Creates a new API builder.
     *
     * @return a new {@link Builder}
     */
    public static Builder initialize() {
        return new Builder();
    }

    public static class Builder {

        private Plugin plugin;
        private Logger logger;
        private boolean debug = false;

        /**
         * Sets the plugin provider.
         *
         * @param plugin the owning plugin
         */
        public Builder provider(Plugin plugin) {
            this.plugin = plugin;
            return this;
        }

        /**
         * Sets the logger.
         *
         * @param logger the logger to use
         */
        public Builder logger(Logger logger) {
            this.logger = logger;
            return this;
        }

        /**
         * Enables or disables debug mode.
         *
         * @param debug whether debug is enabled
         */
        public Builder debug(boolean debug) {
            this.debug = debug;
            return this;
        }

        /**
         * Builds and initializes the GUI API.
         *
         * @return a configured {@link InitializedGuiAPI}
         *
         * @throws NullPointerException if the plugin provider is not set
         */
        public InitializedGuiAPI build() {
            return new InitializedGuiAPI(Objects.requireNonNull(plugin, "Plugin provider not set"),
                    logger != null ? logger : plugin.getLogger(), debug);
        }
    }
}

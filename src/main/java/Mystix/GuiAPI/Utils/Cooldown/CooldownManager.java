package Mystix.GuiAPI.Utils.Cooldown;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages cooldowns for arbitrary objects.
 *
 * <p>This class provides a simple way to associate and track cooldown
 * instances per key (for example: Player, UUID, String, etc.).</p>
 *
 * @param <T> the key type used to identify cooldown owners
 */
public class CooldownManager<T> {

    /** Internal storage of cooldowns mapped to their owner key. */
    private final Map<T, Cooldown> cooldownMap = new HashMap<>();

    /**
     * Gets the remaining cooldown time in seconds for the given object.
     *
     * @param object the key whose cooldown should be checked
     * @return remaining time in seconds, or {@code -1} if no cooldown exists
     */
    public long getTimeLeft(T object) {
        Cooldown cooldown = getCooldown(object);
        if (cooldown == null) return -1;
        return cooldown.getTimeLeft();
    }

    /**
     * Checks whether the given object is currently on cooldown.
     *
     * @param object the key to check
     * @return {@code true} if a cooldown exists and has not finished
     */
    public boolean isOnCooldown(T object) {
        Cooldown cooldown = getCooldown(object);
        return cooldown != null && !cooldown.hasFinished();
    }

    /**
     * Retrieves the cooldown associated with the given object.
     *
     * @param object the key whose cooldown should be retrieved
     * @return the cooldown instance, or {@code null} if none exists
     */
    public Cooldown getCooldown(T object) {
        return cooldownMap.get(object);
    }

    /**
     * Sets or replaces the cooldown for the given object.
     *
     * @param object   the key to associate with the cooldown
     * @param cooldown the cooldown instance to store
     */
    public void setCooldown(T object, Cooldown cooldown) {
        cooldownMap.put(object, cooldown);
    }

    /**
     * Returns an unmodifiable view of the internal cooldown map.
     *
     * @return immutable map of all tracked cooldowns
     */
    public Map<T, Cooldown> getCooldownMap() {
        return Collections.unmodifiableMap(cooldownMap);
    }
}
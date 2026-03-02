package Mystix.GuiAPI.Utils.Cooldown;

/**
 * Represents a simple time-based cooldown.
 *
 * <p>The cooldown starts at object creation time and counts down
 * in seconds until it reaches zero.</p>
 */
public class Cooldown {

    /** Timestamp (ms) when the cooldown was created/started. */
    private final long lastUsed = System.currentTimeMillis();

    /** Cooldown duration in seconds. */
    private final long cooldownTimeNumber;

    /**
     * Creates a new cooldown.
     *
     * @param cooldownTimeNumber duration of the cooldown in seconds
     */
    public Cooldown(long cooldownTimeNumber) {
        this.cooldownTimeNumber = cooldownTimeNumber;
    }

    /**
     * Gets the remaining cooldown time in seconds.
     *
     * @return seconds remaining (maybe negative if expired)
     */
    public long getTimeLeft() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = (currentTime - lastUsed) / 1000;
        return cooldownTimeNumber - elapsedTime;
    }

    /**
     * Checks whether the cooldown has finished.
     *
     * @return {@code true} if the cooldown duration has elapsed
     */
    public boolean hasFinished() {
        return (System.currentTimeMillis() - lastUsed) > (cooldownTimeNumber * 1000);
    }
}
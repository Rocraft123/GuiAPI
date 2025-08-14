package Mystix.GuiAPI.Pack.Functions;

import Mystix.GuiAPI.Events.EntryClickEvent;
import Mystix.GuiAPI.GuiAPI;
import Mystix.GuiAPI.Pack.Models.APIFunction;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class APISound implements APIFunction {

    @Override
    public void activate(EntryClickEvent event, String key) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        try {
            String soundStr = key.split("<")[1].replace(">", "").toUpperCase();
            Sound sound = Sound.valueOf(soundStr);
            player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
        } catch (IllegalArgumentException e) {
            GuiAPI.logger.warning("Failed to find sound from: " +  key);
        } catch (ArrayIndexOutOfBoundsException e) {
            GuiAPI.logger.warning("Could not find a sound key in: " + key);
        }
    }

    @Override
    public String getKey() {
        return "sound";
    }
}


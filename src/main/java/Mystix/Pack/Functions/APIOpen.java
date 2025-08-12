package Mystix.Pack.Functions;

import Mystix.Events.EntryClickEvent;
import Mystix.Gui.Gui;
import Mystix.Gui.GuiManager;
import Mystix.Gui.History.GuiHistory;
import Mystix.GuiAPI.GuiAPI;
import Mystix.Pack.Models.APIFunction;
import Mystix.Pack.Models.GuiPack;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class APIOpen implements APIFunction {

    @Override
    public void activate(EntryClickEvent event, String key) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        String newKey = key.replace("open<", "").replace(">", "");

        if (newKey.equalsIgnoreCase("Variable:Back")) {
            Gui gui = GuiHistory.pop(player.getUniqueId());

            if (gui == null) {
                player.closeInventory();
                return;
            }

            gui.open(player);
        }

        String[] keys = newKey.split("\\|");

        GuiAPI.logger.info("New Key" + newKey + " old Key" + key);
        GuiAPI.logger.info("Keys: " + Arrays.toString(keys));
        if (keys.length != 2) return;

        String packKey = keys[0];
        String guiKey = keys[1];

        GuiPack pack = GuiManager.getPack(packKey);
        if (pack == null) return;
        GuiAPI.logger.info("Found a pack: " + pack.getName());
        Gui gui = pack.getGui(guiKey);

        if (gui == null) return;

        gui.open(player);
    }

    @Override
    public String getKey() {
        return "open";
    }
}

package Mystix.GuiAPI.Pack.Functions;

import Mystix.GuiAPI.Events.EntryClickEvent;
import Mystix.GuiAPI.Pack.Models.APIFunction;
import org.bukkit.entity.Player;

public class APIRefresh implements APIFunction {
    @Override
    public void activate(EntryClickEvent event, String key) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        event.getGui().open(player);
    }

    @Override
    public String getKey() {
        return "refresh";
    }
}


package Mystix.GuiAPI.Pack.Functions;

import Mystix.GuiAPI.Events.EntryClickEvent;
import Mystix.GuiAPI.Pack.Models.APIFunction;
import org.bukkit.Bukkit;

public class APIBroadcast implements APIFunction {

    @Override
    public void activate(EntryClickEvent event, String key) {
        Bukkit.broadcastMessage(key.split("<")[1].replace(">", ""));
    }

    @Override
    public String getKey() {
        return "broadcast";
    }
}

package Mystix.GuiAPI.Pack.Functions;

import Mystix.GuiAPI.Events.EntryClickEvent;
import Mystix.GuiAPI.Pack.Models.APIFunction;

public class APICancel implements APIFunction {

    @Override
    public void activate(EntryClickEvent event, String key) {
        event.setCancelled(true);
    }

    @Override
    public String getKey() {
        return "cancel";
    }
}

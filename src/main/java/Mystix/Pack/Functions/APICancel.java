package Mystix.Pack.Functions;

import Mystix.Events.EntryClickEvent;
import Mystix.Pack.Models.APIFunction;

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

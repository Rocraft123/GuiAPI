package Mystix.Pack.Functions;

import Mystix.Events.EntryClickEvent;
import Mystix.Pack.Models.APIFunction;

public class APIClose implements APIFunction {

    @Override
    public void activate(EntryClickEvent event, String key) {
        event.getWhoClicked().closeInventory();
    }

    @Override
    public String getKey() {
        return "close";
    }
}

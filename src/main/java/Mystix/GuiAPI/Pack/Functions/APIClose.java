package Mystix.GuiAPI.Pack.Functions;

import Mystix.GuiAPI.Events.EntryClickEvent;
import Mystix.GuiAPI.Pack.Models.APIFunction;

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

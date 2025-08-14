package Mystix.GuiAPI.Pack.Models;

import Mystix.GuiAPI.Events.EntryClickEvent;

public interface APIFunction {
    void activate(EntryClickEvent event, String key);
    String getKey();
}

package Mystix.Pack.Models;

import Mystix.Events.EntryClickEvent;

public interface APIFunction {
    void activate(EntryClickEvent event, String key);
    String getKey();
}

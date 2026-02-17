package Mystix.GuiAPI.Event.Events;

import Mystix.GuiAPI.Event.Event;
import Mystix.GuiAPI.Gui.Entry;

public class EntryEvent implements Event {

    private final Entry entry;

    public EntryEvent(Entry entry) {
        this.entry = entry;
    }

    public Entry entry() {
        return entry;
    }
}

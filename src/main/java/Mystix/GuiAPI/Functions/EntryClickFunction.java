package Mystix.GuiAPI.Functions;

import Mystix.GuiAPI.Events.EntryClickEvent;

@FunctionalInterface
public interface EntryClickFunction {
    void execute(EntryClickEvent event);
}


package Mystix.Functions;

import Mystix.Events.EntryClickEvent;

@FunctionalInterface
public interface EntryClickFunction {
    void execute(EntryClickEvent event);
}


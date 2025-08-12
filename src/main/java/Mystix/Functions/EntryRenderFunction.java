package Mystix.Functions;

import Mystix.Events.EntryRenderEvent;

@FunctionalInterface
public interface EntryRenderFunction {
    void execute(EntryRenderEvent event);
}

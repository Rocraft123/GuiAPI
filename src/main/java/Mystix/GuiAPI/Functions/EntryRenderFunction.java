package Mystix.GuiAPI.Functions;

import Mystix.GuiAPI.Events.EntryRenderEvent;

@FunctionalInterface
public interface EntryRenderFunction {
    void execute(EntryRenderEvent event);
}

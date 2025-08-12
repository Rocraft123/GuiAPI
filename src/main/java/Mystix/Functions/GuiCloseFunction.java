package Mystix.Functions;

import Mystix.Events.GuiCloseEvent;

@FunctionalInterface
public interface GuiCloseFunction {
    void execute(GuiCloseEvent event);
}
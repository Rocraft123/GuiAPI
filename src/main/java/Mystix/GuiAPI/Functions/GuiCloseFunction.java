package Mystix.GuiAPI.Functions;

import Mystix.GuiAPI.Events.GuiCloseEvent;

@FunctionalInterface
public interface GuiCloseFunction {
    void execute(GuiCloseEvent event);
}
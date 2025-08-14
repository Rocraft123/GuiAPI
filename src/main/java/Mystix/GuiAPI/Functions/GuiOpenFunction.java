package Mystix.GuiAPI.Functions;

import Mystix.GuiAPI.Events.GuiOpenEvent;

@FunctionalInterface
public interface GuiOpenFunction {
    void execute(GuiOpenEvent event);
}

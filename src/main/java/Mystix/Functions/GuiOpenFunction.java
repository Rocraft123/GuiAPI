package Mystix.Functions;

import Mystix.Events.GuiOpenEvent;

@FunctionalInterface
public interface GuiOpenFunction {
    void execute(GuiOpenEvent event);
}

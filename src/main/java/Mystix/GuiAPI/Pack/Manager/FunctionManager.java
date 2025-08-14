package Mystix.GuiAPI.Pack.Manager;

import Mystix.GuiAPI.GuiAPI;
import Mystix.GuiAPI.Pack.Functions.*;
import Mystix.GuiAPI.Pack.Models.APIFunction;

import java.util.ArrayList;
import java.util.List;

public class FunctionManager {

    private static final List<APIFunction> functions = new ArrayList<>();

    public static APIFunction getFunction(String full) {
        String[] args = full.split(":", 2);
        if (args.length != 2) return null;
        if (!args[0].equalsIgnoreCase("APIFunction")) return null;

        String keyWithArgs = args[1];
        String baseKey = keyWithArgs.contains("<") ? keyWithArgs.substring(0, keyWithArgs.indexOf('<')) : keyWithArgs;

        for (APIFunction function : functions) {
            if (function.getKey().equalsIgnoreCase(baseKey)) {
                return function;
            }
        }

        GuiAPI.logger.info("Could not find API function with the key: " + baseKey);
        return null;
    }

    public static void registerFunctions() {
        functions.add(new APIBroadcast());
        functions.add(new APICancel());
        functions.add(new APIClose());
        functions.add(new APIOpen());
        functions.add(new APIRefresh());
        functions.add(new APISound());
    }

    public static List<APIFunction> getFunctions() {
        return functions;
    }
}

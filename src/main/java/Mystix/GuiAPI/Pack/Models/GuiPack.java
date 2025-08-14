package Mystix.GuiAPI.Pack.Models;

import Mystix.GuiAPI.Gui.Gui;
import Mystix.GuiAPI.Pack.Loader.GuiPackLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GuiPack {

    private final String name;
    private final File path;

    private final HashMap<File, Gui> guis = new HashMap<>();

    private final GuiPackLoader loader;

    public GuiPack(String name, GuiPackLoader loader) {
        this.name = name;
        this.loader = loader;
        this.path = loader.getPackFolder();
    }

    public void addGui(File file, Gui gui) {
        guis.put(file, gui);
    }

    public String getName() {
        return name;
    }

    public File getPath() {
        return path;
    }

    public GuiPackLoader getLoader() {
        return loader;
    }

    public Gui getGui(File file) {
        return guis.get(file);
    }

    public Gui getGui(String title) {
        for (Gui gui : getGuis()) {
            if (gui.getTitle(true).replace(" ", "").equalsIgnoreCase(title.replace(" ", "")))
                return gui;
        }

        return null;
    }

    public List<Gui> getGuis() {
        return new ArrayList<>(this.guis.values());
    }
}

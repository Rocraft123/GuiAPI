package Mystix.Pack.Loader;

import Mystix.Gui.Gui;
import Mystix.Pack.Models.GuiPack;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class GuiPackLoader {

    private final File packFolder;
    private final Plugin plugin;

    public GuiPackLoader(File packFolder, Plugin plugin) {
        this.packFolder = packFolder;
        this.plugin = plugin;
    }

    public GuiPack loadPack() {
        File packFile = new File(packFolder, "Pack.yml");
        if (!packFile.exists()) return null;
        YamlConfiguration pack = YamlConfiguration.loadConfiguration(packFile);
        if (!isValid(pack)) {
            plugin.getLogger().warning("Pack does not support current plugin version: " + packFolder);
            return null;
        }

        GuiPack guiPack = new GuiPack(packFolder.getName(),this);
        File guiFiles = new File(packFolder, "Guis");

        loadGuis(guiFiles, guiPack);
        return guiPack;
    }

    private void loadGuis(File guis, GuiPack pack) {
        if (!guis.exists()) return;

        plugin.getLogger().info("Loading guis for: " + pack.getName());
        try {
            for (File file : Objects.requireNonNull(guis.listFiles())) {
                Gui gui = new GuiLoader(file, pack).load();

                plugin.getLogger().info("loaded gui for: " + pack.getName() + " Gui: " + gui.getTitle(true));
                pack.addGui(file, gui);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed loading guis", e);
        }
    }

    public boolean isValid(YamlConfiguration pack) {
        String expected = plugin.getDescription().getVersion();
        String provided = pack.getString("API-Version");
        return expected.equalsIgnoreCase(provided);
    }

    public File getPackFolder() {
        return packFolder;
    }

    public File getGuisPath() {
        return new File(packFolder, "guis");
    }

    public File getEntriesPath() {
        return new File(packFolder, "entries");
    }
}

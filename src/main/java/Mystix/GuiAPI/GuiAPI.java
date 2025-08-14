package Mystix.GuiAPI;

import Mystix.GuiAPI.Commands.GuiPack.ListGuis;
import Mystix.GuiAPI.Commands.GuiPack.ListPacks;
import Mystix.GuiAPI.Commands.GuiPack.OpenGuiExtension;
import Mystix.GuiAPI.Commands.GuiPack.ReloadPacks;
import Mystix.GuiAPI.Commands.Utils.Serialize;
import Mystix.GuiAPI.Gui.*;
import Mystix.GuiAPI.Listeners.EntryClickListener;
import Mystix.GuiAPI.Pack.Manager.FunctionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class GuiAPI extends JavaPlugin {

    public static Logger logger;

    @Override
    public void onEnable() {
        logger = this.getLogger();
        logger.info("Enabling GuiAPI...");

        Bukkit.getPluginManager().registerEvents(new GuiManager(),this);
        Bukkit.getPluginManager().registerEvents(new EntryClickListener(),this);

        FunctionManager.registerFunctions();
        GuiManager.loadPacks(this);

        Mystix.GuiAPI.Commands.Command command = new Mystix.GuiAPI.Commands.Command();
        command.registerExtensions(new ListGuis());
        command.registerExtensions(new OpenGuiExtension());
        command.registerExtensions(new ListPacks());
        command.registerExtensions(new ReloadPacks());

        Objects.requireNonNull(getCommand("Serialize")).setExecutor(new Serialize());
        Objects.requireNonNull(getCommand("GuiPack")).setExecutor(command);

        logger.info("Enabled GuiAPI");
    }

    @Override
    public void onDisable() {
        logger.info("Disabling GuiAPI...");

        logger.info("Disabled GuiAPI");
    }
}

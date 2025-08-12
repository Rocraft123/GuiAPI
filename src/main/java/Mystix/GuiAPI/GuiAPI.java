package Mystix.GuiAPI;

import Mystix.Commands.GuiPack.ListGuis;
import Mystix.Commands.GuiPack.ListPacks;
import Mystix.Commands.GuiPack.OpenGuiExtension;
import Mystix.Commands.GuiPack.ReloadPacks;
import Mystix.Commands.Utils.Serialize;
import Mystix.Gui.*;
import Mystix.Listeners.EntryClickListener;
import Mystix.Pack.Manager.FunctionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

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

        getCommand("Serialize").setExecutor(new Serialize());

        Mystix.Commands.Command command = new Mystix.Commands.Command();
        getCommand("GuiPack").setExecutor(command);
        command.registerExtensions(new ListGuis());
        command.registerExtensions(new OpenGuiExtension());
        command.registerExtensions(new ListPacks());
        command.registerExtensions(new ReloadPacks());

        logger.info("Enabled GuiAPI");
    }

    @Override
    public void onDisable() {
        logger.info("Disabling GuiAPI...");

        logger.info("Disabled GuiAPI");
    }
}

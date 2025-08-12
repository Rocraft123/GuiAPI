package Mystix.Commands.GuiPack;

import Mystix.Commands.CommandExtension;
import Mystix.Gui.GuiManager;
import Mystix.GuiAPI.GuiAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReloadPacks extends CommandExtension {

    public ReloadPacks() {
        super("reload", "will reload all the packs");
        setPermission("Gui.Manager");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        GuiManager.reloadPacks(JavaPlugin.getProvidingPlugin(GuiAPI.class));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        return Collections.emptyList();
    }
}

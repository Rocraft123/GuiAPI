package Mystix.GuiAPI.Commands.GuiPack;

import Mystix.GuiAPI.Commands.CommandExtension;
import Mystix.GuiAPI.Gui.GuiManager;
import Mystix.GuiAPI.Pack.Models.GuiPack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListPacks extends CommandExtension {

    public ListPacks() {
        super("list", "will send the player a list of all loaded packs");
        setPermission("Gui.Manager");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        Collection<GuiPack> packs = GuiManager.getLoadedPacks();

        if (packs.isEmpty()) {
            sender.sendMessage(Component.text("[Warning]").color(TextColor.color(255, 197, 38)).decorate(TextDecoration.BOLD)
                    .append(Component.text("No GUI packs are currently loaded.", NamedTextColor.RED)));
            return true;
        }

        sender.sendMessage(Component.text("Loaded GUI Packs:", NamedTextColor.GOLD));
        for (GuiPack pack : packs) {
            Component line = Component.text(" - ", NamedTextColor.DARK_GRAY)
                    .append(Component.text(pack.getName(), NamedTextColor.AQUA));
            sender.sendMessage(line);
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        return Collections.emptyList();
    }
}

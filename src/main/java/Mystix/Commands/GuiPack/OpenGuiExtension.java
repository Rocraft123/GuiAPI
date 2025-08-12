package Mystix.Commands.GuiPack;

import Mystix.Commands.CommandExtension;
import Mystix.Gui.Gui;
import Mystix.Gui.GuiManager;
import Mystix.Pack.Models.GuiPack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class OpenGuiExtension extends CommandExtension {

    public OpenGuiExtension() {
        super("open", "Opens a GUI from a pack");
        setPermission("Gui.Manager");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Only players can open GUIs.", NamedTextColor.RED));
            return true;
        }

        if (args.length < 3) {
            player.sendMessage(Component.text("Usage: /GuiPack open <packName> <guiTitle>", NamedTextColor.RED));
            return true;
        }

        String packName = args[1];
        String guiTitle = args[2];

        GuiPack pack = GuiManager.getPack(packName);
        if (pack == null) {
            player.sendMessage(Component.text("[Warning] ").color(TextColor.color(255, 197, 38)).decorate(TextDecoration.BOLD)
                    .append(Component.text("GUI pack not found: " + packName, NamedTextColor.RED)
                            .decoration(TextDecoration.BOLD, false)));
            return true;
        }

        Gui gui = pack.getGui(guiTitle);
        if (gui == null) {
            player.sendMessage(Component.text("[Warning] ").color(TextColor.color(255, 197, 38)).decorate(TextDecoration.BOLD)
                    .append(Component.text("GUI not found: " + guiTitle, NamedTextColor.RED)
                            .decoration(TextDecoration.BOLD, false)));
            return true;
        }

        gui.open(player);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 2) {
            for (GuiPack pack : GuiManager.getLoadedPacks()) {
                suggestions.add(pack.getName());
            }
        }

        if (args.length == 3) {
            GuiPack pack = GuiManager.getPack(args[1]);
            if (pack != null) {
                for (Gui gui : pack.getGuis()) {
                    suggestions.add(gui.getTitle(true).toLowerCase().replace(" ", ""));
                }
            }
        }

        return suggestions;
    }
}


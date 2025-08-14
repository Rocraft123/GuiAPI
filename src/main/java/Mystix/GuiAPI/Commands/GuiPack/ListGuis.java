package Mystix.GuiAPI.Commands.GuiPack;

import Mystix.GuiAPI.Commands.CommandExtension;
import Mystix.GuiAPI.Gui.Gui;
import Mystix.GuiAPI.Gui.GuiManager;
import Mystix.GuiAPI.Pack.Models.GuiPack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListGuis extends CommandExtension {

    public ListGuis() {
        super("listGuis", "will send the player a list of guis all guis");
        setPermission("Gui.Manager");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (args.length < 2) {
            sender.sendMessage(Component.text("Usage: /listGuis <packName>", NamedTextColor.YELLOW));
            return true;
        }

        GuiPack pack = GuiManager.getPack(args[1]);
        if (pack == null) {
            sender.sendMessage(Component.text("[Warning] ").color(TextColor.color(255, 197, 38)).decorate(TextDecoration.BOLD)
                    .append(Component.text("Could not find a GuiPack with the name: " + args[1]).color(NamedTextColor.RED)
                            .decoration(TextDecoration.BOLD, false)));
            return true;
        }

        Collection<Gui> guis = pack.getGuis();
        if (guis.isEmpty()) {
            sender.sendMessage(Component.text("This pack does not contain any GUIs.", NamedTextColor.GRAY));
            return true;
        }

        sender.sendMessage(Component.text("GUIs in pack '" + pack.getName() + "':", NamedTextColor.GOLD));
        for (Gui gui : guis) {
            sender.sendMessage(Component.text(" - ", NamedTextColor.DARK_GRAY)
                    .append(gui.getTitle().clickEvent(ClickEvent.runCommand("/GuiPack open " + args[1] + " " + gui.getTitle(true).
                            toLowerCase().replace(" ", "")))));
        }
        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        List<String> collections = new ArrayList<>();
        if (args.length == 2) {
            for (GuiPack pack : GuiManager.getLoadedPacks())
                collections.add(pack.getName());
        }
        return collections;
    }
}
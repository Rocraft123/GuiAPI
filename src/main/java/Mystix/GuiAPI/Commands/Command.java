package Mystix.GuiAPI.Commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Command implements CommandExecutor, TabCompleter {

    private final List<CommandExtension> extensions = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length == 0) {
            sendHelpMessage(sender, label);
            return true;
        }

        for (CommandExtension extension : extensions) {
            if (extension.getArg().equalsIgnoreCase(args[0])) {
                if (extension.getPermission() != null && !sender.hasPermission(extension.getPermission()))
                    return true;
                return extension.onCommand(sender, command, label, args);
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String @NotNull[] args) {
        List<CommandExtension> accessibleExtensions = new ArrayList<>();

        for (CommandExtension extension : this.extensions) {
            if (extension.getPermission() == null || sender.hasPermission(extension.getPermission()))
                accessibleExtensions.add(extension);
        }

        if (args.length == 1) return tabCompleteFirstArg(args[0].toLowerCase(), accessibleExtensions);
        if (args.length >= 2) return tabCompleteSubArgs(sender, command, label, args, accessibleExtensions);
        return Collections.emptyList();
    }

    private List<String> tabCompleteFirstArg(String input, List<CommandExtension> extensions) {
        return extensions.stream()
                .map(CommandExtension::getArg)
                .filter(arg -> arg.toLowerCase().startsWith(input))
                .toList();
    }

    private List<String> tabCompleteSubArgs(CommandSender sender, org.bukkit.command.Command command, String label, String[] args, List<CommandExtension> extensions) {
        String sub = args[0];
        String current = args[args.length - 1].toLowerCase();

        for (CommandExtension extension : extensions) {
            if (extension.getArg().equalsIgnoreCase(sub)) {
                List<String> complete = extension.onTabComplete(sender, command, label, args);
                if (complete == null) return Collections.emptyList();
                return complete.stream()
                        .filter(s -> s.toLowerCase().startsWith(current))
                        .toList();
            }
        }
        return Collections.emptyList();
    }


    private void sendHelpMessage(CommandSender sender, String label) {
        sender.sendMessage(Component.text("Available commands:").color(NamedTextColor.YELLOW));
        for (CommandExtension extension : extensions) {
            if (extension.getPermission() == null || sender.hasPermission(extension.getPermission())) {
                sender.sendMessage(Component.text("/" + label + " " + extension.getArg() + " - " + extension.getDescription())
                        .color(NamedTextColor.GRAY));
            }
        }
    }

    public void registerExtensions(CommandExtension extension) {
        extensions.add(extension);
    }

    public void unRegisterExtensions(CommandExtension extension) {
        extensions.remove(extension);
    }
}
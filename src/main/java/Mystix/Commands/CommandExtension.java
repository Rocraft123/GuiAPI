package Mystix.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class CommandExtension {

    private final String arg;
    private final String description;
    private String permission;

    public CommandExtension(String arg, String description) {
        this.arg = arg;
        this.description = description;
    }

    public String getArg() {
        return arg;
    }

    public String getDescription() {
        return description;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public abstract boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String @NotNull [] args);
    public abstract @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args);
}
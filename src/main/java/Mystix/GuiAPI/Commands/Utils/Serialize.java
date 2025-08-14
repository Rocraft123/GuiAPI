package Mystix.GuiAPI.Commands.Utils;

import Mystix.GuiAPI.Utils.Serializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Serialize implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) return true;

        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack.isEmpty()) {
            player.sendMessage(
                    Component.text("[Warning] ").color(TextColor.color(255, 197, 38)).decorate(TextDecoration.BOLD)
                            .append(Component.text("Make sure you have a item in your main hand").color(NamedTextColor.RED)
                                    .decoration(TextDecoration.BOLD, false)));
            return true;
        }

        Serializer<ItemStack> serializer = new Serializer<>();
        String serialized;
        try {
             serialized = serializer.serialize(itemStack);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        player.sendMessage(Component.text("Click to copy the item string").color(NamedTextColor.GREEN).clickEvent(
                ClickEvent.clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, serialized))
                .hoverEvent(Component.text("Click to Copy").color(NamedTextColor.GRAY)));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        return Collections.emptyList();
    }
}

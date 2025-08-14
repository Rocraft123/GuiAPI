package Mystix.GuiAPI.Listeners;

import Mystix.GuiAPI.Gui.Entry;
import Mystix.GuiAPI.Gui.Gui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;

public class EntryClickListener implements Listener {

    @EventHandler
    public void onClick(Mystix.GuiAPI.Events.EntryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        Gui gui = event.getGui();
        Entry entry = event.getEntry();

        if (!event.getClick().equals(ClickType.MIDDLE) || !player.isOp()) return;
        if (!gui.isDebugEnabled()) return;

        player.sendMessage(Component.text("🔧 GUI Debug Info", NamedTextColor.YELLOW).decorate(TextDecoration.BOLD));
        player.sendMessage(Component.text("• GUI Title: ", NamedTextColor.GRAY)
                .append(gui.getTitle().color(NamedTextColor.AQUA)));
        player.sendMessage(Component.text("• Slot: ", NamedTextColor.GRAY)
                .append(Component.text(event.getSlot(), NamedTextColor.GREEN)));

        if (entry != null && entry.getItem() != null) {
            player.sendMessage(Component.text("• Entry Material: ", NamedTextColor.GRAY)
                    .append(Component.text(entry.getItem().getType().name(), NamedTextColor.BLUE)));
            player.sendMessage(Component.text("• Has Click Handler: ", NamedTextColor.GRAY)
                    .append(Component.text(entry.getOnClick() != null, NamedTextColor.GOLD)));
            player.sendMessage(Component.text("• Has Render Handler: ", NamedTextColor.GRAY)
                    .append(Component.text(entry.getOnRender() != null, NamedTextColor.GOLD)));
            player.sendMessage(Component.text("• tags: ", NamedTextColor.GRAY)
                    .append(Component.text(entry.getTags() != null ? entry.getTags().toString() : "Empty", NamedTextColor.DARK_AQUA)));
        } else
            player.sendMessage(Component.text("• No entry found at this slot.", NamedTextColor.RED));
    }
}

package Mystix.Gui.History;

import Mystix.Gui.Gui;
import org.bukkit.entity.Player;

import java.util.*;

public class GuiHistory {

    private static final Map<UUID, Deque<Gui>> guiHistory = new HashMap<>();

    public static void push(Player player, Gui gui) {
        guiHistory.computeIfAbsent(player.getUniqueId(), k -> new ArrayDeque<>()).push(gui);
    }

    public static Gui pop(Player player) {
        Deque<Gui> stack = guiHistory.get(player.getUniqueId());
        if (stack != null && !stack.isEmpty()) {
            return stack.pop();
        }
        return null;
    }

    public static Gui peek(Player player) {
        Deque<Gui> stack = guiHistory.get(player.getUniqueId());
        return (stack != null && !stack.isEmpty()) ? stack.peek() : null;
    }

    public static void clear(Player player) {
        guiHistory.remove(player.getUniqueId());
    }

    public static void push(UUID uuid, Gui gui) {
        guiHistory.computeIfAbsent(uuid, k -> new ArrayDeque<>()).push(gui);
    }

    public static Gui pop(UUID uuid) {
        Deque<Gui> stack = guiHistory.get(uuid);
        if (stack != null && !stack.isEmpty()) {
            return stack.pop();
        }
        return null;
    }

    public static Gui peek(UUID uuid) {
        Deque<Gui> stack = guiHistory.get(uuid);
        return (stack != null && !stack.isEmpty()) ? stack.peek() : null;
    }

    public static void clear(UUID uuid) {
        guiHistory.remove(uuid);
    }
}
